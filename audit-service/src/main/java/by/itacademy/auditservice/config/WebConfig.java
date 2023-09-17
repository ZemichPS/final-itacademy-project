package by.itacademy.auditservice.config;

import by.itacademy.auditservice.core.converters.AuditEntityToAuditRecordConverter;
import by.itacademy.auditservice.core.converters.AuditEventRecordToEntityConverter;
import by.itacademy.auditservice.core.converters.AuditRecordToEntityConverter;
import by.itacademy.auditservice.core.converters.PageToPageOfRecordConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new AuditEntityToAuditRecordConverter());
        registry.addConverter(new PageToPageOfRecordConverter());
        registry.addConverter(new AuditRecordToEntityConverter());
        registry.addConverter(new AuditEventRecordToEntityConverter());

    }
}
