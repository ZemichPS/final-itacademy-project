package by.ITAcademy.UserMicroService.config;


import by.ITAcademy.UserMicroService.services.utils.LocalDateTimeToMilliFormatter;
import by.ITAcademy.UserMicroService.services.utils.converters.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new PageToPageOfDtoConverter());
        registry.addConverter(new UserCreateDtoToUserEntityConverter());
        registry.addConverter(new UserEntityToUserDtoConverter());
        registry.addConverter(new UserRegistrationDtoToUserEntityConverter());
        registry.addConverter(new UserUpdateDtoToUserEntityConverter());
        registry.addFormatter(new LocalDateTimeToMilliFormatter());
    }
}
