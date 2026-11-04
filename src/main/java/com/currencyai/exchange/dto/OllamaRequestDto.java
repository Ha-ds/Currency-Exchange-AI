package com.currencyai.exchange.dto;

public record OllamaRequestDto(
        String model,
        String prompt,
        boolean stream
) {
}
