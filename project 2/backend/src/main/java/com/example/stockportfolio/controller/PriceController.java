package com.CapX.stockBackend.controller;

import com.CapX.stockBackend.model.Price;
import com.CapX.stockBackend.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/prices")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class PriceController {
    private final PriceService priceService;

    @GetMapping("/{ticker}/current")
    public ResponseEntity<BigDecimal> getCurrentPrice(@PathVariable String ticker) {
        return ResponseEntity.ok(priceService.getCurrentPrice(ticker));
    }

    @GetMapping("/{ticker}/historical")
    public ResponseEntity<List<Price>> getHistoricalPrices(
            @PathVariable String ticker,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return ResponseEntity.ok(priceService.getHistoricalPrices(ticker, startDate, endDate));
    }

    @GetMapping("/top50")
    public ResponseEntity<List<Price>> getTop50Stocks() {
        return ResponseEntity.ok(priceService.getTop50Stocks());
    }
}