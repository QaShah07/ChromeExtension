package com.example.stockportfolio.service;

import com.example.stockportfolio.model.Price;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Override
    public BigDecimal getCurrentPrice(String ticker) {
        String url = String.format("%s/daily/%s/prices?token=%s", apiUrl, ticker.toLowerCase(), apiKey);
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    url, HttpMethod.GET, entity, String.class);

            // Log response status and body
            System.out.println("API Status Code: " + responseEntity.getStatusCode());
            System.out.println("Raw JSON Response: " + responseEntity.getBody());

            String response = responseEntity.getBody();

            if (response == null || response.isBlank()) {
                throw new RuntimeException("Received empty response from Tiingo API for " + ticker);
            }

            List<Price> prices = objectMapper.readValue(response, new TypeReference<>() {});
            return (prices != null && !prices.isEmpty()) ? prices.get(0).getClose() : BigDecimal.ZERO;

        } catch (JsonProcessingException e) {
            System.err.println("JSON Parsing Error: " + e.getMessage());
            throw new RuntimeException("Failed to parse price data for " + ticker, e);
        } catch (Exception e) {
            System.err.println("API Request Error: " + e.getMessage());
            throw new RuntimeException("Failed to fetch price data for " + ticker, e);
        }
    }

    @Override
    public List<Price> getHistoricalPrices(String ticker, String startDate, String endDate) {
        String url = String.format("%s/daily/%s/prices?startDate=%s&endDate=%s&token=%s",
                apiUrl, ticker, startDate, endDate, apiKey);
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    url, HttpMethod.GET, entity, String.class);

            System.out.println("Historical Data API Response: " + responseEntity.getBody());

            String response = responseEntity.getBody();
            return objectMapper.readValue(response, new TypeReference<>() {});

        } catch (JsonProcessingException e) {
            System.err.println("JSON Parsing Error: " + e.getMessage());
            throw new RuntimeException("Failed to parse historical data for " + ticker, e);
        } catch (Exception e) {
            System.err.println("API Request Error: " + e.getMessage());
            throw new RuntimeException("Failed to fetch historical data for " + ticker, e);
        }
    }

    @Override
    public List<Price> getTop50Stocks() {
        // Tiingo doesn't provide a direct API for top stocks
        return Collections.emptyList();
    }

    @PostConstruct
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
    }
}
