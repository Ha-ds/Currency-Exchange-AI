package com.currencyai.exchange.service;

import com.currencyai.exchange.dto.CurrencyExchangePromptRequestDto;
import com.currencyai.exchange.dto.ExchangeRateResponseDto;
import com.currencyai.exchange.dto.HistoricalRateRequestDto;
import com.currencyai.exchange.external.ExchangeRateAPIService;
import com.currencyai.exchange.external.LocalModelAIService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.currencyai.exchange.constants.CurrencyExchangeAIPromptConstants.EXCHANGE_RATE_QUESTION_PROMPT;
import static com.currencyai.exchange.constants.CurrencyExchangeAIPromptConstants.HISTORICAL_RATE_ANALYSIS_PROMPT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CurrencyExchangeAIServiceTest {

    @Mock
    private ExchangeRateAPIService exchangeRateAPIService;

    @Mock
    private LocalModelAIService localModelAIService;

    @InjectMocks
    private CurrencyExchangeAIService currencyExchangeAIService;

    private HistoricalRateRequestDto historicalRequest;

    private List<ExchangeRateResponseDto> historicalRates;

    @BeforeEach
    void setUp() {

        // Given
        historicalRequest = new HistoricalRateRequestDto(
                "EUR",
                "USD",
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 1, 31)
        );

        historicalRates = List.of(
                new ExchangeRateResponseDto(
                        LocalDate.of(2025, 1, 2),
                        "EUR",
                        "USD",
                        new BigDecimal("1.03")
                ),
                new ExchangeRateResponseDto(
                        LocalDate.of(2025, 1, 3),
                        "EUR",
                        "USD",
                        new BigDecimal("1.04")
                )
        );
    }

    @Test
    void getHistoricalRates_shouldReturnHistoricalRates() {

        // Given
        when(exchangeRateAPIService.getHistoricalRates(historicalRequest))
                .thenReturn(historicalRates);

        // When
        List<ExchangeRateResponseDto> result =
                currencyExchangeAIService.getHistoricalRates(historicalRequest);


        // Then
        assertNotNull(result);
        assertEquals(historicalRates, result);

        verify(exchangeRateAPIService, times(1))
                .getHistoricalRates(historicalRequest);

        verifyNoInteractions(localModelAIService);
    }

    @Test
    void analyze_shouldReturnAIAnalysis() {

        // Given
        String expectedAnalysis =
                "The EUR appreciated against the USD during this period.";

        when(exchangeRateAPIService.getHistoricalRates(historicalRequest))
                .thenReturn(historicalRates);

        when(localModelAIService.ask(anyString()))
                .thenReturn(expectedAnalysis);

        // When
        String result = currencyExchangeAIService.analyze(historicalRequest);

        // Then
        assertNotNull(result);
        assertEquals(expectedAnalysis, result);

        verify(exchangeRateAPIService, times(1))
                .getHistoricalRates(historicalRequest);

        verify(localModelAIService, times(1))
                .ask(anyString());
    }

    @Test
    void analyze_shouldConstructCorrectPrompt() {

        // Given
        String expectedAnalysis = "Exchange rate analysis";

        when(exchangeRateAPIService.getHistoricalRates(historicalRequest))
                .thenReturn(historicalRates);

        when(localModelAIService.ask(anyString()))
                .thenReturn(expectedAnalysis);

        ArgumentCaptor<String> promptCaptor =
                ArgumentCaptor.forClass(String.class);

        // When
        currencyExchangeAIService.analyze(historicalRequest);

        // Then
        verify(localModelAIService).ask(promptCaptor.capture());

        String expectedPrompt = HISTORICAL_RATE_ANALYSIS_PROMPT.formatted(
                historicalRequest.base(),
                historicalRequest.quote(),
                historicalRequest.from(),
                historicalRequest.to(),
                historicalRates
        );

        assertEquals(expectedPrompt, promptCaptor.getValue());
    }

    @Test
    void analyze_shouldThrowExceptionWhenFetchingRatesFails() {

        // Given
        when(exchangeRateAPIService.getHistoricalRates(historicalRequest))
                .thenThrow(new RuntimeException("Frankfurter API error"));

        // When
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> currencyExchangeAIService.analyze(historicalRequest)
        );

        // Then
        assertEquals("Frankfurter API error", exception.getMessage());

        verifyNoInteractions(localModelAIService);
    }

    @Test
    void prompt_shouldReturnAIResponse() {

        // Given
        String question = "Did the EUR appreciate against the USD?";

        String expectedResponse =
                "Yes, the EUR appreciated against the USD.";

        CurrencyExchangePromptRequestDto request =
                new CurrencyExchangePromptRequestDto(
                        historicalRequest,
                        question
                );

        when(exchangeRateAPIService.getHistoricalRates(historicalRequest))
                .thenReturn(historicalRates);

        when(localModelAIService.ask(anyString()))
                .thenReturn(expectedResponse);

        // When
        String result = currencyExchangeAIService.prompt(request);

        // Then
        assertNotNull(result);
        assertEquals(expectedResponse, result);

        verify(exchangeRateAPIService, times(1))
                .getHistoricalRates(historicalRequest);

        verify(localModelAIService, times(1))
                .ask(anyString());
    }

    @Test
    void prompt_shouldConstructCorrectPrompt() {

        // Given
        String question = "What was the highest exchange rate?";

        CurrencyExchangePromptRequestDto request =
                new CurrencyExchangePromptRequestDto(
                        historicalRequest,
                        question
                );

        when(exchangeRateAPIService.getHistoricalRates(historicalRequest))
                .thenReturn(historicalRates);

        when(localModelAIService.ask(anyString()))
                .thenReturn("The highest rate was 1.04.");

        ArgumentCaptor<String> promptCaptor =
                ArgumentCaptor.forClass(String.class);

        // When
        currencyExchangeAIService.prompt(request);

        // Then
        verify(localModelAIService).ask(promptCaptor.capture());

        String expectedPrompt = EXCHANGE_RATE_QUESTION_PROMPT.formatted(
                historicalRequest.base(),
                historicalRequest.quote(),
                historicalRequest.from(),
                historicalRequest.to(),
                historicalRates,
                question
        );

        assertEquals(expectedPrompt, promptCaptor.getValue());
    }

    @Test
    void prompt_shouldPropagateExceptionWhenAIServiceFails() {

        // Given
        CurrencyExchangePromptRequestDto request =
                new CurrencyExchangePromptRequestDto(
                        historicalRequest,
                        "Explain the exchange rate movement."
                );

        when(exchangeRateAPIService.getHistoricalRates(historicalRequest))
                .thenReturn(historicalRates);

        when(localModelAIService.ask(anyString()))
                .thenThrow(new IllegalStateException("Ollama error"));

        // When
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> currencyExchangeAIService.prompt(request)
        );

        // Then
        assertEquals("Ollama error", exception.getMessage());

        verify(exchangeRateAPIService, times(1))
                .getHistoricalRates(historicalRequest);
    }
}

