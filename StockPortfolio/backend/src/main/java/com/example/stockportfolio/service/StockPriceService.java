//package com.example.stockportfolio.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import java.math.BigDecimal;
//
//@Service
//@RequiredArgsConstructor
//public class StockPriceService {
//    private final RestTemplate restTemplate;
//
//    @Value("${tiingo.api.key}")
//    private String apiKey;
//
//    @Value("${tiingo.api.url}")
//    private String apiUrl;
//
//    public BigDecimal getCurrentPrice(String ticker) {
//        String url = String.format("%s/daily/%s/prices", apiUrl, ticker);
//        try {
//            // For now, return a random price between 90% and 110% of 100
//            // This is temporary until we properly integrate with Tiingo API
//            double randomFactor = 0.9 + Math.random() * 0.2;
//            return BigDecimal.valueOf(100 * randomFactor).setScale(2, BigDecimal.ROUND_HALF_UP);
//        } catch (Exception e) {
//            // Return a default value in case of API failure
//            return BigDecimal.ZERO;
//        }
//    }
//}