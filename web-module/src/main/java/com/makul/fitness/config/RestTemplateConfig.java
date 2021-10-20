package com.makul.fitness.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
        @Bean
        public RestTemplate createRestTemplate(RestTemplateBuilder builder) {
            RestTemplate restTemplate= builder.build();
            return restTemplate;
        }
}
