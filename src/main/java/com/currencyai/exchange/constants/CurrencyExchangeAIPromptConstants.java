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

    public static final String EXCHANGE_RATE_QUESTION_PROMPT = """
        You are a currency exchange assistant.

        Answer the user's question using the historical
        exchange-rate data provided below.

        Base currency: %s
        Quote currency: %s
        Period: %s to %s

        Historical exchange-rate data:
        %s

        User's question:
        %s

        Base your answer on the provided exchange-rate data.
        Do not invent exchange rates or unsupported facts.
        If the data is insufficient to answer the question,
        clearly explain what information is missing.
        """;
}
