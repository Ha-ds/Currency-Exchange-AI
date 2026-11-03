package com.currencyai.exchange.service;

import com.currencyai.exchange.dto.ExchangeRateResponse;
import com.currencyai.exchange.dto.HistoricalRateRequest;
import com.currencyai.exchange.external.ExchangeRateAPIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CurrencyExchangeAIService {

    private final ExchangeRateAPIService exchangeRateAPIService;

    /**
     * Retrieves historical exchange rates for the requested currency pair and date range.
     * Calls {@link ExchangeRateAPIService} to get the response.
     */
    public List<ExchangeRateResponse> getHistoricalRates(
            HistoricalRateRequest request) {

        log.info("Getting historical rates for {} to {}", request.base(), request.quote());
        return exchangeRateAPIService.getHistoricalRates(request);
    }
}
