package com.currencyai.exchange.service;

import com.currencyai.exchange.dto.ExchangeRateResponseDto;
import com.currencyai.exchange.dto.HistoricalRateRequestDto;
import com.currencyai.exchange.external.ExchangeRateAPIService;
import com.currencyai.exchange.external.LocalModelAIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CurrencyExchangeAIService {

    private final ExchangeRateAPIService exchangeRateAPIService;
    private final LocalModelAIService localModelAIService;

    /**
     * Retrieves historical exchange rates for the requested currency pair and date range.
     * Calls {@link ExchangeRateAPIService} to get the response.
     */
    public List<ExchangeRateResponseDto> getHistoricalRates(
            HistoricalRateRequestDto request) {

        log.info("Getting historical rates for {} to {}", request.base(), request.quote());
        return exchangeRateAPIService.getHistoricalRates(request);
    }

    /**
     *This method asks the AI model to analyze the retrieved historical
     * exchange rates' movements.
     */
    public String analyze(HistoricalRateRequestDto request) {

        log.info("Analyzing exchange rates for {} to {}",
                request.base(), request.quote());

        List<ExchangeRateResponseDto> rates = getHistoricalRates(request);

        String prompt = """
            You are a currency exchange analysis assistant.

            Analyze the historical exchange rates for %s to %s
            between %s and %s.

            Explain whether the base currency appreciated or
            depreciated against the quote currency.

            Identify the overall trend and relevant fluctuations.
            Support your explanation using the provided data.
            Do not invent exchange rates or movements.

            Historical exchange-rate data:
            %s
            """.formatted(
                request.base(),
                request.quote(),
                request.from(),
                request.to(),
                rates
        );

        String analysis = localModelAIService.ask(prompt);

        log.info("Successfully analyzed exchange rates for {} to {}",
                request.base(), request.quote());

        return analysis;

    }
}
