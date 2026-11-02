package com.currencyai.exchange.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    private static final String FRANKFURTER_API_URL =
            "https://api.frankfurter.dev/v2";

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl(FRANKFURTER_API_URL)
                .build();
    }
}
