package com.example.stockportfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.example.stockportfolio.model")
public class StockPortfolioApplication {
    public static void main(String[] args) {
        SpringApplication.run(StockPortfolioApplication.class, args);
    }
}