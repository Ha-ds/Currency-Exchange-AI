package com.currencyai.exchange.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record HistoricalRateRequest(

        @NotBlank(message = "Base currency is required")
        @Pattern(regexp = "^[A-Z]{3}$",
                message = "Base currency must be a 3-letter uppercase code")
        String base,

        @NotBlank(message = "Quote currency is required")
        @Pattern(regexp = "^[A-Z]{3}$",
                message = "Quote currency must be a 3-letter uppercase code")
        String quote,

        @NotNull(message = "Start date is required")
        LocalDate from,

        @NotNull(message = "End date is required")
        LocalDate to
) {

    @AssertTrue(message = "Start date must be before or equal to end date")
    public boolean isValidDateRange() {
        return from == null || to == null || !from.isAfter(to);
    }
}
