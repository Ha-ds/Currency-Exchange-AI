package com.currencyai.exchange.service;

import com.currencyai.exchange.dto.ExchangeRateResponse;
import com.currencyai.exchange.dto.HistoricalRateRequest;
import com.currencyai.exchange.external.ExchangeRateAPIService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyExchangeAIService {

    private final ExchangeRateAPIService exchangeRateAPIService;

    public List<ExchangeRateResponse> getHistoricalRates(
            HistoricalRateRequest request) {

        return exchangeRateAPIService.getHistoricalRates(request);
    }
}
