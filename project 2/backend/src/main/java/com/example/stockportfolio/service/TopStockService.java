package com.CapX.stockBackend.service;

import com.CapX.stockBackend.model.TopStock;
import com.CapX.stockBackend.repository.TopStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import jakarta.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class TopStockService {
    private final TopStockRepository topStockRepository;
    private final Random random = new Random();

    @PostConstruct
    public void initializeTopStocks() {
        if (topStockRepository.count() == 0) {
            // Sample data for top stocks
            String[][] stocksData = {
                    {"Apple Inc.", "AAPL"},
                    {"Microsoft Corporation", "MSFT"},
                    {"Amazon.com Inc.", "AMZN"},
                    {"NVIDIA Corporation", "NVDA"},
                    {"Alphabet Inc.", "GOOGL"},
                    {"Meta Platforms Inc.", "META"},
                    {"Tesla, Inc.", "TSLA"},
                    {"Berkshire Hathaway", "BRK.A"},
                    {"UnitedHealth Group", "UNH"},
                    {"Johnson & Johnson", "JNJ"},
                    {"JPMorgan Chase", "JPM"},
                    {"Visa Inc.", "V"},
                    {"Procter & Gamble", "PG"},
                    {"Mastercard Inc.", "MA"},
                    {"Home Depot Inc.", "HD"}
            };

            for (String[] stockData : stocksData) {
                TopStock stock = new TopStock();
                stock.setName(stockData[0]);
                stock.setSymbol(stockData[1]);
                updateRandomPrice(stock);
                topStockRepository.save(stock);
            }
        }
    }

    public List<TopStock> getTop50Stocks() {
        List<TopStock> stocks = topStockRepository.findAll();
        stocks.forEach(this::updateRandomPrice);
        return stocks;
    }

    private void updateRandomPrice(TopStock stock) {
        double basePrice = 100.0;
        double randomFactor = 0.9 + random.nextDouble() * 0.2;
        stock.setCurrentPrice(BigDecimal.valueOf(basePrice * randomFactor));
        stock.setPriceChangePercent(BigDecimal.valueOf((randomFactor - 1) * 100));
        stock.setMarketCap(BigDecimal.valueOf(random.nextDouble() * 1000000000));
        stock.setVolume(random.nextLong(1000000));
    }
}