package com.example.stockportfolio.service;

import com.example.stockportfolio.dto.PortfolioMetricsDTO;
import com.example.stockportfolio.dto.PortfolioWeight;
import com.example.stockportfolio.model.Stock;
import com.example.stockportfolio.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final StockRepository stockRepository;

    public List<PortfolioWeight> getPortfolioWeightage() {
        List<Stock> stocks = stockRepository.findAll();

        // Calculate total current portfolio value
        BigDecimal totalCurrentValue = stocks.stream()
                .map(stock -> stock.getCurrentPrice().multiply(BigDecimal.valueOf(stock.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Handle case where total value is zero to avoid division by zero
        if (totalCurrentValue.compareTo(BigDecimal.ZERO) == 0) {
            return List.of();
        }

        // Calculate weightage for each stock
        return stocks.stream()
                .map(stock -> {
                    BigDecimal stockValue = stock.getCurrentPrice().multiply(BigDecimal.valueOf(stock.getQuantity()));
                    BigDecimal weightage = stockValue.multiply(BigDecimal.valueOf(100))
                            .divide(totalCurrentValue, 2, RoundingMode.HALF_UP);
                    return new PortfolioWeight(stock.getName(), weightage.doubleValue());
                })
                .collect(Collectors.toList());
    }

    public PortfolioMetricsDTO getPortfolioMetrics() {
        List<Stock> stocks = stockRepository.findAll();
        PortfolioMetricsDTO metrics = new PortfolioMetricsDTO();

        BigDecimal totalInvestment = stocks.stream()
                .map(stock -> stock.getBuyPrice().multiply(BigDecimal.valueOf(stock.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal currentValue = stocks.stream()
                .map(stock -> stock.getCurrentPrice().multiply(BigDecimal.valueOf(stock.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalGainLoss = currentValue.subtract(totalInvestment);

        BigDecimal gainLossPercentage = totalInvestment.compareTo(BigDecimal.ZERO) != 0
                ? totalGainLoss.multiply(BigDecimal.valueOf(100)).divide(totalInvestment, 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        metrics.setTotalInvestment(totalInvestment);
        metrics.setCurrentValue(currentValue);
        metrics.setTotalGainLoss(totalGainLoss);
        metrics.setGainLossPercentage(gainLossPercentage);

        return metrics;
    }
}