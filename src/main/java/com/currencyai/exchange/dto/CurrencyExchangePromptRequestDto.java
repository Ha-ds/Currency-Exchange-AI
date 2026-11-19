package com.currencyai.exchange.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CurrencyExchangePromptRequestDto(

        @NotNull(message = "Historical rate request is required")
        @Valid
        HistoricalRateRequestDto historicalRateRequest,

        @NotBlank(message = "Question is required")
        String question
) {
}
