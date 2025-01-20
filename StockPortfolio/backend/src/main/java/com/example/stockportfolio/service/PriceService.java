package com.example.stockportfolio.service;

import com.example.stockportfolio.model.Price;
import java.math.BigDecimal;
import java.util.List;

public interface PriceService {
    BigDecimal getCurrentPrice(String ticker);
    List<Price> getHistoricalPrices(String ticker, String startDate, String endDate);
    List<Price> getTop50Stocks();
}