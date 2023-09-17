package by.itacademy.auditservice.config;

import by.itacademy.auditservice.endpoints.filter.JwtFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter filter) throws Exception  {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers(HttpMethod.POST, "/audit").hasAnyRole("SYSTEM")
                                .requestMatchers("/audit/**").hasAnyRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/audit").hasAnyRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling((exceptionHandling) ->
                        exceptionHandling.authenticationEntryPoint((request, response, ex) ->
                                        response.setStatus(
                                                HttpServletResponse.SC_UNAUTHORIZED
                                        ))
                                .accessDeniedHandler((request, response, ex) ->
                                        response.setStatus(
                                                HttpServletResponse.SC_FORBIDDEN
                                        ))
                )
                .addFilterBefore(
                        filter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}