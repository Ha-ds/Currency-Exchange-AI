package com.currencyai.exchange.utils;

import com.currencyai.exchange.dto.CurrencyExchangePromptRequestDto;
import com.currencyai.exchange.dto.HistoricalRateRequestDto;
import com.currencyai.exchange.external.LocalModelAIService;
import com.currencyai.exchange.service.CurrencyExchangeAIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationRunner {

    private final LocalModelAIService localModelAIService;
    private final CurrencyExchangeAIService currencyExchangeAIService;

    /**
     * Method responsible for running a currency
     * exchange analysis test after startup.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {

        HistoricalRateRequestDto request = new HistoricalRateRequestDto(
                "EUR",
                "USD",
                LocalDate.now().minusDays(30),
                LocalDate.now()
        );

        log.info("Starting currency exchange AI analysis test...");
        try {
            String response = currencyExchangeAIService.analyze(request);
            log.info("AI analysis result: {}", response);

        } catch (Exception exception) {
            log.error("Currency exchange AI analysis failed.", exception);
        }

        try {
            log.info("Starting currency exchange AI prompt test...");

            CurrencyExchangePromptRequestDto promptRequest =
                    new CurrencyExchangePromptRequestDto(
                            request,
                            "On which date was the EUR strongest against the USD?"
                    );

            String response =
                    currencyExchangeAIService.prompt(promptRequest);

            log.info("AI prompt response: {}", response);

        } catch (Exception exception) {
            log.error("Currency exchange AI prompt test failed.", exception);
        }

    }
}
