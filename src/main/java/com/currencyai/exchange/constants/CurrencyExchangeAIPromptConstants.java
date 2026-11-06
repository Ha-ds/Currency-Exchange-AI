package com.currencyai.exchange.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CurrencyExchangeAIPromptConstants {


    public static final String HISTORICAL_RATE_ANALYSIS_PROMPT = """
            You are a currency exchange analysis assistant.

            Analyze the historical exchange rates for %s to %s
            between %s and %s.

            Explain whether the base currency appreciated or
            depreciated against the quote currency.

            Identify the overall trend and relevant fluctuations.
            Support your explanation using the provided data.
            Do not invent exchange rates or movements.

            Historical exchange-rate data:
            %s
            """;
}
