package com.example.stockportfolio.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "top_stocks")
public class TopStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String symbol;
    private BigDecimal currentPrice;
    private BigDecimal priceChangePercent;
    private BigDecimal marketCap;
    private Long volume;
}