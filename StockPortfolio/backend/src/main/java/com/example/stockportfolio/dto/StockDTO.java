package com.example.stockportfolio.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class StockDTO {
    private Long id;
    private String name;
    private String ticker;
    private Integer quantity;
    private BigDecimal buyPrice;
    private BigDecimal currentPrice;
}