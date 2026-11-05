package com.currencyai.exchange.controller;

import com.currencyai.exchange.dto.ExchangeRateResponseDto;
import com.currencyai.exchange.dto.HistoricalRateRequestDto;
import com.currencyai.exchange.service.CurrencyExchangeAIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/exchange-rates")
@RequiredArgsConstructor
public class ExchangeRateController {

    private  final CurrencyExchangeAIService currencyExchangeAIService;

    @PostMapping("/get-historical-rates")
    public List<ExchangeRateResponseDto> getHistoricalRates(
            @Valid @RequestBody HistoricalRateRequestDto request) {

        return currencyExchangeAIService.getHistoricalRates(request);

    }
}
