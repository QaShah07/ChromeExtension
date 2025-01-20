package com.CapX.stockBackend.repository;

import com.CapX.stockBackend.model.TopStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopStockRepository extends JpaRepository<TopStock, Long> {
}