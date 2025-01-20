package com.CapX.stockBackend.controller;

import com.CapX.stockBackend.model.TopStock;
import com.CapX.stockBackend.service.TopStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/top-stocks")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class TopStockController {
    private final TopStockService topStockService;

    @GetMapping
    public ResponseEntity<List<TopStock>> getTop50Stocks() {
        return ResponseEntity.ok(topStockService.getTop50Stocks());
    }
}