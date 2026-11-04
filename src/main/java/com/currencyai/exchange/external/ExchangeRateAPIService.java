package com.currencyai.exchange.external;

import com.currencyai.exchange.dto.ExchangeRateResponseDto;
import com.currencyai.exchange.dto.HistoricalRateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExchangeRateAPIService {

    @Qualifier("frankfurterRestClient")
    private final RestClient restClient;

    /**
     * This method retrieves historical exchange rates for the requested currency
     * pair and date range from the FrankfurterAPI.
     */
    public List<ExchangeRateResponseDto> getHistoricalRates(
            HistoricalRateRequestDto request) {

        log.info(
                "Fetching historical exchange rates: {} to {}, from {} to {}",
                request.base(),
                request.quote(),
                request.from(),
                request.to()
        );

        String uri = UriComponentsBuilder
                .fromPath("/rates")
                .queryParam("base", request.base())
                .queryParam("quotes", request.quote())
                .queryParam("from", request.from())
                .queryParam("to", request.to())
                .build()
                .encode()
                .toUriString();

        try {
            log.info("Calling Frankfurter API with URI: {}", uri);
            ExchangeRateResponseDto[] response = restClient.get()
                .uri(uri)
                .retrieve()
                .body(ExchangeRateResponseDto[].class);

            log.info("Received response from Frankfurter API: {}", Arrays.toString(response));
            return response == null ? List.of() : Arrays.asList(response);

    } catch (RestClientResponseException restClientResponseException) {
            log.error("Frankfurter API returned an error: {}", restClientResponseException.getStatusCode());
            throw new RuntimeException(
                    "Frankfurter API returned an error. Status: "
                            + restClientResponseException.getStatusCode()
                            + ", Response: " + restClientResponseException.getResponseBodyAsString(),
                    restClientResponseException
            );

        } catch (ResourceAccessException resourceAccessException) {
            log.error("Could not connect to Frankfurter API: {}", resourceAccessException.getMessage());
            throw new RuntimeException(
                    "Could not connect to Frankfurter API.",
                    resourceAccessException
            );
        }
    }
}
