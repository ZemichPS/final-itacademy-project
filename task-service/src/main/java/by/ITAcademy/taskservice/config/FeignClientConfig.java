package by.ITAcademy.taskservice.config;

import feign.RequestInterceptor;
import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;


@Configuration
public class FeignClientConfig {
    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }
}
