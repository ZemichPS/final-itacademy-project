package by.ITAcademy.taskservice.sevice.impl;


import by.ITAcademy.taskservice.sevice.api.IProducerEventService;
import by.itacademy.sharedresource.core.dto.AuditEventRecord;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Log4j2
@Service
public class KafkaProducerService implements IProducerEventService {

    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    private final String topicName = "TaskManagerMicroServiceEvents";

    public KafkaProducerService(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void saveEvent(AuditEventRecord record) {

        Message<AuditEventRecord> message = MessageBuilder.withPayload(record)
                .setHeader(KafkaHeaders.KEY, record.type())
                .setHeader(KafkaHeaders.TOPIC, topicName)
                .build();

        CompletableFuture<SendResult<String, byte[]>> future = kafkaTemplate.send(message);
        future.whenComplete(((result, ex) -> {
            if (ex == null) {
                log.info("Event was sent successfully. Event type: "+ record.type().name() );
            } else {
                log.error("Failed in sending event. Event type: "+ record.type().name() + ". Exception: " + ex.getMessage() + " Cause is: " + ex.getCause());
            }
        }));
    }
}
