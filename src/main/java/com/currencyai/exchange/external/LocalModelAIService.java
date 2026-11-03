package com.currencyai.exchange.external;

import com.currencyai.exchange.dto.OllamaRequest;
import com.currencyai.exchange.dto.OllamaResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Service
@Slf4j
public class LocalModelAIService {

    private final RestClient restClient;
    private final String model;

    public LocalModelAIService(
            @Qualifier("ollamaRestClient") RestClient restClient,
            @Value("${ollama.model:llama3.2}") String model) {

        this.restClient = restClient;
        this.model = model;
    }

    /**
     * This method sends a question to the local Ollama AI model
     * and returns its generated response.
     */
    public String ask(String question) {

        log.info("Sending question to Ollama model: {}", model);
        try {
            OllamaRequest request =
                    new OllamaRequest(model, question, false);

            OllamaResponse response = restClient.post()
                    .uri("/api/generate")
                    .body(request)
                    .retrieve()
                    .body(OllamaResponse.class);

            if (response == null || response.response() == null) {
                throw new IllegalStateException(
                        "Ollama returned an empty response.");
            }

            log.info("Successfully received AI response.");
            return response.response();

        } catch (RestClientResponseException restClientResponseException) {

            log.error("Ollama HTTP error: {}", restClientResponseException.getStatusCode(), restClientResponseException);
            throw new IllegalStateException(
                    "Ollama returned an error.", restClientResponseException);

        } catch (ResourceAccessException resourceAccessException) {

            log.error("Could not connect to Ollama.", resourceAccessException);
            throw new IllegalStateException(
                    "Could not connect to Ollama. Check whether it is running.", resourceAccessException);
        }
    }


}
