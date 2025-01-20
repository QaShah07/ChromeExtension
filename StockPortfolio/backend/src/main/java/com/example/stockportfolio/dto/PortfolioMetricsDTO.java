package com.example.stockportfolio.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PortfolioMetricsDTO {
    private BigDecimal totalInvestment;
    private BigDecimal currentValue;
    private BigDecimal totalGainLoss;
    private BigDecimal gainLossPercentage;
}