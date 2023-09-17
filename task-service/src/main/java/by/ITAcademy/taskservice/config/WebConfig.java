package by.ITAcademy.taskservice.config;


import by.ITAcademy.taskservice.endpoint.converters.*;
import by.ITAcademy.taskservice.endpoint.formatters.LocalDateTimeToMilliFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new UserDtoToUserDetailsConverter());
        registry.addConverter(new ProjectEntityToProjectDtoConverter());
        registry.addConverter(new TaskEntityToTaskDtoConverter());
        registry.addConverter(new ProjectCreateDtoToProjectEntityConverter());
        registry.addConverter(new PageTaskConverter());

        registry.addFormatter(new LocalDateTimeToMilliFormatter());
    }
}
