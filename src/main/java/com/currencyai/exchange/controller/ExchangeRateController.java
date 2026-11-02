package com.currencyai.exchange.controller;

import com.currencyai.exchange.dto.ExchangeRateResponse;
import com.currencyai.exchange.dto.HistoricalRateRequest;
import com.currencyai.exchange.service.CurrencyExchangeAIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/exchange-rates")
@RequiredArgsConstructor
public class ExchangeRateController {

    private  final CurrencyExchangeAIService currencyExchangeAIService;

    public List<ExchangeRateResponse> getHistoricalRates(
            @Valid @RequestBody HistoricalRateRequest request) {

        return currencyExchangeAIService.getHistoricalRates(request);

    }
}
