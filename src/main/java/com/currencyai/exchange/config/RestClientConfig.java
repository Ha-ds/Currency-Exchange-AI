package com.currencyai.exchange.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean("frankfurterRestClient")
    public RestClient frankfurterRestClient(
            @Value("${frankfurter.base-url}") String baseUrl) {

        return RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Bean("ollamaRestClient")
    public RestClient ollamaRestClient(
            @Value("${ollama.base-url:http://localhost:11434}") String baseUrl) {

        return RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
}
