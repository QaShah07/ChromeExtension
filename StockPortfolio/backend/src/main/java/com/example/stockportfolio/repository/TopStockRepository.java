package com.example.stockportfolio.repository;

import com.example.stockportfolio.model.TopStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopStockRepository extends JpaRepository<TopStock, Long> {
}