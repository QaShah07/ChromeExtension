package com.example.stockportfolio.repository;

import com.example.stockportfolio.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Long> {
}
