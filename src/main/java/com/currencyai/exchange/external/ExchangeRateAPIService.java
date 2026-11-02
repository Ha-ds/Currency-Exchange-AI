package com.currencyai.exchange.external;

import com.currencyai.exchange.dto.ExchangeRateResponse;
import com.currencyai.exchange.dto.HistoricalRateRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@Service
public class ExchangeRateAPIService {

    private final RestClient restClient;

    public ExchangeRateAPIService(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://api.frankfurter.dev/v2")
                .build();
    }

    public List<ExchangeRateResponse> getHistoricalRates(
            HistoricalRateRequest request) {

        String uri = UriComponentsBuilder
                .fromPath("/rates")
                .queryParam("base", request.base())
                .queryParam("quotes", request.quote())
                .queryParam("from", request.from())
                .queryParam("to", request.to())
                .build()
                .encode()
                .toUriString();

        try{
        ExchangeRateResponse[] response = restClient.get()
                .uri(uri)
                .retrieve()
                .body(ExchangeRateResponse[].class);

        return response == null ? List.of() : Arrays.asList(response);

    } catch (RestClientResponseException restClientResponseException) {
            throw new RuntimeException(
                    "Frankfurter API returned an error. Status: "
                            + restClientResponseException.getStatusCode()
                            + ", Response: " + restClientResponseException.getResponseBodyAsString(),
                    restClientResponseException
            );

        } catch (ResourceAccessException resourceAccessException) {
            throw new RuntimeException(
                    "Could not connect to Frankfurter API.",
                    resourceAccessException
            );
        }
    }
}
