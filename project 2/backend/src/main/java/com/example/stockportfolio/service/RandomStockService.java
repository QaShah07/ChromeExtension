package com.CapX.stockBackend.service;

import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class RandomStockService {
    // List of popular stocks with their names and tickers
    private final List<String[]> AVAILABLE_STOCKS = Arrays.asList(
            new String[]{"Apple Inc.", "AAPL"},
            new String[]{"Microsoft Corporation", "MSFT"},
            new String[]{"Amazon.com Inc.", "AMZN"},
            new String[]{"Alphabet Inc.", "GOOGL"},
            new String[]{"Meta Platforms Inc.", "META"},
            new String[]{"NVIDIA Corporation", "NVDA"},
            new String[]{"Tesla Inc.", "TSLA"},
            new String[]{"JPMorgan Chase & Co.", "JPM"},
            new String[]{"Johnson & Johnson", "JNJ"},
            new String[]{"Visa Inc.", "V"},
            new String[]{"Walmart Inc.", "WMT"},
            new String[]{"UnitedHealth Group Inc.", "UNH"},
            new String[]{"Mastercard Inc.", "MA"},
            new String[]{"Home Depot Inc.", "HD"},
            new String[]{"Bank of America Corp.", "BAC"}
    );

    public List<String[]> getRandomStocks(int count) {
        if (count > AVAILABLE_STOCKS.size()) {
            throw new IllegalArgumentException("Requested count exceeds available stocks");
        }

        return new Random()
                .ints(0, AVAILABLE_STOCKS.size())
                .distinct()
                .limit(count)
                .mapToObj(AVAILABLE_STOCKS::get)
                .collect(Collectors.toList());
    }
}