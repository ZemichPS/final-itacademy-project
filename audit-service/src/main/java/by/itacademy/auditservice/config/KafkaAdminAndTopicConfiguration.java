package by.itacademy.auditservice.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaAdminAndTopicConfiguration {
    private final KafkaProperties kafkaProperties;

    public KafkaAdminAndTopicConfiguration(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> configs = kafkaProperties.buildAdminProperties();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        return new KafkaAdmin(configs);
    }

    @Bean
    public KafkaAdmin.NewTopics userMicroServiceTopic() {
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name("auditServiceEvents")
                        .partitions(1)
                        .replicas(1)
                        .config(TopicConfig.COMPRESSION_TYPE_CONFIG, "zstd")
                        .build()

        );

    }





}
