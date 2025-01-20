package com.example.stockportfolio.controller;

import com.example.stockportfolio.dto.PortfolioMetricsDTO;
import com.example.stockportfolio.dto.PortfolioWeight;
import com.example.stockportfolio.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portfolio")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class PortfolioController {
    private final PortfolioService portfolioService;

    @GetMapping("/weightage")
    public ResponseEntity<List<PortfolioWeight>> getPortfolioWeightage() {
        return ResponseEntity.ok(portfolioService.getPortfolioWeightage());
    }

    @GetMapping("/metrics")
    public ResponseEntity<PortfolioMetricsDTO> getPortfolioMetrics() {
        return ResponseEntity.ok(portfolioService.getPortfolioMetrics());
    }
}