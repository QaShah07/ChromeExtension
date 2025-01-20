package com.CapX.stockBackend.service;

import com.CapX.stockBackend.model.Price;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${tiingo.api.key}")
    private String apiKey;

    @Value("${tiingo.api.url}")
    private String apiUrl;

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Token " + apiKey);
        return headers;
    }

    @Override
    public BigDecimal getCurrentPrice(String ticker) {
        String url = String.format(apiUrl, ticker, apiKey);
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());

        try {
            String response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class
            ).getBody();

            List<Price> prices = objectMapper.readValue(response, new TypeReference<>() {});
            return prices != null && !prices.isEmpty() ? prices.get(0).getClose() : BigDecimal.ZERO;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse price data for " + ticker, e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch price data for " + ticker, e);
        }
    }

    @Override
    public List<Price> getHistoricalPrices(String ticker, String startDate, String endDate) {
        String url = String.format("%s/daily/%s/prices?startDate=%s&endDate=%s",
                apiUrl, ticker, startDate, endDate);
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());

        try {
            String response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class
            ).getBody();

            return objectMapper.readValue(response, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse historical data for " + ticker, e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch historical data for " + ticker, e);
        }
    }

    @Override
    public List<Price> getTop50Stocks() {
        // Tiingo doesn't have a direct endpoint for top stocks
        // You would need to maintain a list of major stocks and fetch their prices
        // For now, returning an empty list
        return Collections.emptyList();
    }
}