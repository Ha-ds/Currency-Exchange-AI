package com.currencyai.exchange.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExchangeRateResponseDto(
        LocalDate date,
        String base,
        String quote,
        BigDecimal rate
) {
}
