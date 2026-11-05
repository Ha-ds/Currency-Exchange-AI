package com.currencyai.exchange.utils;

import com.currencyai.exchange.external.LocalModelAIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationRunner {

    private final LocalModelAIService localModelAIService;

    /**
     * Method responsible for running a test question
     * against the local AI model after startup.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {

        log.info("Starting Ollama test...");
        try {
            String response = localModelAIService.ask(
                    "Explain Currency Exchange Rate in one sentence."
            );
            log.info("Ollama response: {}", response);

        } catch (Exception exception) {
            log.error("Ollama test failed: {}", exception.getMessage(), exception);
        }
    }
}
