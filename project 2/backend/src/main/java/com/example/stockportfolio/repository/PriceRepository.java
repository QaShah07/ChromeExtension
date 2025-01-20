package com.CapX.stockBackend.repository;

import com.CapX.stockBackend.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Long> {
}
