package by.itacademy.auditservice.service.impl;

import by.itacademy.auditservice.service.api.IEventProcessor;
import by.itacademy.auditservice.service.api.IKafkaListener;
import by.itacademy.sharedresource.core.dto.AuditEventRecord;
import by.itacademy.sharedresource.core.dto.AuditRecord;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class AuditKafkaListener implements IKafkaListener {
    private final IEventProcessor<AuditEventRecord> recordIEventProcessor;

    public AuditKafkaListener(IEventProcessor<AuditEventRecord> recordIEventProcessor) {
        this.recordIEventProcessor = recordIEventProcessor;
    }

    @Override
    @RetryableTopic(kafkaTemplate = "retryableTopicKafkaTemplate")
    @KafkaListener(
            id = "audit-client",
            topics = "auditServiceEvents",
            containerGroup = "audit-group"
    )
    public void listen(AuditEventRecord record) {
      System.out.println(record);
       recordIEventProcessor.process(record);

    }

    @DltHandler
    private void dltHandle(
            ConsumerRecord<String, AuditRecord> record,
            @Header(KafkaHeaders.ORIGINAL_OFFSET) byte[] offset,
            @Header(KafkaHeaders.EXCEPTION_FQCN) String descException,
            @Header(KafkaHeaders.EXCEPTION_STACKTRACE) String stacktrace,
            @Header(KafkaHeaders.EXCEPTION_MESSAGE) String errorMessage
    ) {
        log.info("Failed in processing events \n " +
                "Cause by: " + errorMessage + "\n" +
                "Topic is: " + record.topic() + "\n" +
                "Offset is: " + record.offset() + "\n" +
                "TimeStamp is: " + record.timestamp() + "\n" +
                "desc exception : " + descException + "\n" +
                "Stacktrace is: " + stacktrace + "\n" +
                "Offset is: " + offset + "\n"
        );
    }
}
