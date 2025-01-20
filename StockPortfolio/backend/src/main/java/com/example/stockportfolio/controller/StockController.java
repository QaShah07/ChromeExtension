package com.example.stockportfolio.controller;

import com.example.stockportfolio.dto.PortfolioMetricsDTO;
import com.example.stockportfolio.dto.StockDTO;
import com.example.stockportfolio.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class StockController {
    private final StockService stockService;

    @GetMapping("/stocks")
    public ResponseEntity<List<StockDTO>> getAllStocks() {
        return ResponseEntity.ok(stockService.getAllStocks());
    }

    @PostMapping("/stocks")
    public ResponseEntity<StockDTO> addStock(@Valid @RequestBody StockDTO stockDTO) throws Exception {
        return ResponseEntity.ok(stockService.addStock(stockDTO));
    }

    @PutMapping("/stocks/{id}")
    public ResponseEntity<StockDTO> updateStock(@PathVariable Long id, @Valid @RequestBody StockDTO stockDTO) throws Exception {
        return ResponseEntity.ok(stockService.updateStock(id, stockDTO));
    }

    @DeleteMapping("/stocks/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
        return ResponseEntity.ok().build();
    }
}