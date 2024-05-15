package com.productservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

public class WebClientConfig {
    
    @Value("${filestorage-service.base.url}")
    private String fileStorageBaseURL;

    @Bean
    public WebClient webClient() {
        return WebClient
                .builder()
                .baseUrl(fileStorageBaseURL)
                .build();
    }
}
