package com.example.stockportfolio.repository;

import com.example.stockportfolio.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
    boolean existsByTicker(String ticker);
}