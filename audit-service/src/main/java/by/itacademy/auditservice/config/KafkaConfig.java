package by.itacademy.auditservice.config;


import by.itacademy.sharedresource.core.dto.AuditEventRecord;
import by.itacademy.sharedresource.core.dto.UserAudit;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.ImmutableTag;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaRetryTopic;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.retrytopic.RetryTopicConfiguration;
import org.springframework.kafka.retrytopic.RetryTopicConfigurationBuilder;
import org.springframework.kafka.support.JacksonUtils;
import org.springframework.kafka.support.converter.ByteArrayJsonMessageConverter;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.mapping.Jackson2JavaTypeMapper;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.backoff.FixedBackOff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableKafkaRetryTopic
@Configuration
@Slf4j
public class KafkaConfig {
    private final KafkaProperties kafkaProperties;
    private final MeterRegistry meterRegistry;

    public KafkaConfig(KafkaProperties kafkaProperties, MeterRegistry meterRegistry) {
        this.kafkaProperties = kafkaProperties;
        this.meterRegistry = meterRegistry;
    }

    @Bean
    ObjectMapper objectMapper() {
        return JacksonUtils.enhancedObjectMapper();
    }


    @Bean
    TaskScheduler scheduler() {
        return new ThreadPoolTaskScheduler();
    }


    @Bean
    public ConsumerFactory<String, byte[]> consumerFactory(KafkaProperties kafkaProperties, ObjectMapper mapper) {

        Map<String, Object> props = kafkaProperties.buildConsumerProperties();
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
       // props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 100);

        props.put(ConsumerConfig.CLIENT_ID_CONFIG, "audit-client");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "audit-group");
        props.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, "10767980");

        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, true);

        DefaultKafkaConsumerFactory<String, byte[]> consumerFactory = new DefaultKafkaConsumerFactory<>(props);
        // observability
        List<Tag> tags = new ArrayList<>();
        tags.add(new ImmutableTag("kafka_consumer_name", "AuditServiceConsumer"));
        consumerFactory.addListener(new MicrometerConsumerListener<>(meterRegistry, tags));

        return consumerFactory;
    }

    @Bean
    public RetryTopicConfiguration retryTopicConfiguration(KafkaTemplate<String, byte[]> template) {
        return RetryTopicConfigurationBuilder
                .newInstance()
                .fixedBackOff(2000)
                .timeoutAfter(5000)
                .maxAttempts(3)
                .autoCreateTopics(true,1, (short) 1)
                .includeTopics(List.of("auditServiceEvents"))
                .create(template);
    }
    @Bean
    public DefaultErrorHandler errorHandler() {
        DefaultErrorHandler handler = new DefaultErrorHandler((m, e) -> {
            log.info("Failed in processing events \n " +
                    "Cause by: " + e.getCause().getMessage() + "\n" +
                    "Topic is: " + m.topic() + "\n" +
                    "Offset is: " + m.offset() + "\n" +
                    "TimeStamp is: " + m.timestamp() + "\n" +
                    "Object is: " + m.value() + "\n"
            );

        }, new FixedBackOff(10L, 3L));
        handler.addNotRetryableExceptions(IllegalArgumentException.class);
        return handler;
    }

    @Bean
    RecordMessageConverter recordMessageConverter(ObjectMapper mapper) {
        ByteArrayJsonMessageConverter converter = new ByteArrayJsonMessageConverter(mapper);
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID);
        typeMapper.addTrustedPackages("*");
        Map<String, Class<?>> mappings = new HashMap<>();
        mappings.put("auditEventRecord", AuditEventRecord.class);
        mappings.put("userAudit", UserAudit.class);
        typeMapper.setIdClassMapping(mappings);
        converter.setTypeMapper(typeMapper);
        return converter;
    }

    @Bean
    public KafkaListenerContainerFactory<?> kafkaListenerContainerFactory(
            ConsumerFactory<String, byte[]> consumerFactory,
            RecordMessageConverter converter,
            DefaultErrorHandler errorHandler
    ) {
        ConcurrentKafkaListenerContainerFactory<String, byte[]> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);
        factory.setBatchListener(false);
        factory.setConcurrency(1);
        factory.setCommonErrorHandler(errorHandler);
        factory.setRecordMessageConverter(converter);
        factory.getContainerProperties().setPollTimeout(1_000);
        return factory;
    }


    @Bean
    public ProducerFactory<String, byte[]> producerFactory(KafkaProperties kafkaProperties) {
        Map<String, Object> propsMap = kafkaProperties.buildProducerProperties();
        propsMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        propsMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);

        DefaultKafkaProducerFactory<String, byte[]> producerFactory = new DefaultKafkaProducerFactory<>(propsMap);

        return new DefaultKafkaProducerFactory<>(propsMap);
    }

    @Bean("retryableTopicKafkaTemplate")
    public KafkaTemplate<String, byte[]> kafkaTemplate(ProducerFactory<String, byte[]> producerFactory,
                                                       RecordMessageConverter converter) {
        KafkaTemplate<String, byte[]> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        kafkaTemplate.setMessageConverter(converter);
        return kafkaTemplate;
    }

}
