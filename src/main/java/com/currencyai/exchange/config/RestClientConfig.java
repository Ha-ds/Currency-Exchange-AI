package com.currencyai.exchange.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ReactorClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

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

        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(30));

        return RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(
                        new ReactorClientHttpRequestFactory(httpClient)
                )
                .build();
    }
}
