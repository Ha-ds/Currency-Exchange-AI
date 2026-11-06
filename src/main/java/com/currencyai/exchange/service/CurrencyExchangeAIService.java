package com.currencyai.exchange.service;

import com.currencyai.exchange.dto.ExchangeRateResponseDto;
import com.currencyai.exchange.dto.HistoricalRateRequestDto;
import com.currencyai.exchange.external.ExchangeRateAPIService;
import com.currencyai.exchange.external.LocalModelAIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.currencyai.exchange.constants.CurrencyExchangeAIPromptConstants.HISTORICAL_RATE_ANALYSIS_PROMPT;

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

        List<ExchangeRateResponseDto> rates =
                exchangeRateAPIService.getHistoricalRates(request);

        String prompt = HISTORICAL_RATE_ANALYSIS_PROMPT.formatted(
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
