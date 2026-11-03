package com.currencyai.exchange.dto;

public record OllamaRequest(
        String model,
        String prompt,
        boolean stream
) {
}
