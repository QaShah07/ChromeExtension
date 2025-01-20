package com.example.stockportfolio.service;

import com.example.stockportfolio.dto.StockDTO;
import com.example.stockportfolio.exception.StockNotFoundException;
import com.example.stockportfolio.model.Stock;
import com.example.stockportfolio.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;
    private final RandomStockService randomStockService;
    private final PriceService priceService;


    @Transactional
    public List<StockDTO> initializeRandomPortfolio() {
        // Delete existing portfolio
        stockRepository.deleteAll();

        // Get 5 random stocks
        List<String[]> randomStocks = randomStockService.getRandomStocks(5);

        return randomStocks.stream()
                .map(stockInfo -> {
                    Stock stock = new Stock();
                    stock.setName(stockInfo[0]);
                    stock.setTicker(stockInfo[1]);
                    stock.setQuantity(new Random().nextInt(100) + 1);
                    stock.setBuyPrice(BigDecimal.valueOf(10 + new Random().nextDouble() * 990)
                            .setScale(2, RoundingMode.HALF_UP));

                    // Fetch real-time price
                    BigDecimal currentPrice = priceService.getCurrentPrice(stockInfo[1]);
                    stock.setCurrentPrice(currentPrice != null ? currentPrice : stock.getBuyPrice());

                    return stockRepository.save(stock);
                })
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public List<StockDTO> getAllStocks() {
        return stockRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public StockDTO addStock(StockDTO stockDTO) {
        Stock stock = new Stock();
        stock.setName(stockDTO.getName());
        stock.setTicker(stockDTO.getTicker());
        stock.setQuantity(stockDTO.getQuantity());
        stock.setBuyPrice(stockDTO.getBuyPrice());
        // Set initial current price same as buy price
        stock.setCurrentPrice(stockDTO.getBuyPrice());

        Stock savedStock = stockRepository.save(stock);
        return convertToDTO(savedStock);
    }

    public StockDTO updateStock(Long id, StockDTO stockDTO) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new StockNotFoundException("Stock not found with id: " + id));

        stock.setName(stockDTO.getName());
        stock.setTicker(stockDTO.getTicker());
        stock.setQuantity(stockDTO.getQuantity());
        stock.setBuyPrice(stockDTO.getBuyPrice());
        // Preserve current price

        Stock updatedStock = stockRepository.save(stock);
        return convertToDTO(updatedStock);
    }

    public void deleteStock(Long id) {
        if (!stockRepository.existsById(id)) {
            throw new StockNotFoundException("Stock not found with id: " + id);
        }
        stockRepository.deleteById(id);
    }

    private StockDTO convertToDTO(Stock stock) {
        StockDTO dto = new StockDTO();
        dto.setId(stock.getId());
        dto.setName(stock.getName());
        dto.setTicker(stock.getTicker());
        dto.setQuantity(stock.getQuantity());
        dto.setBuyPrice(stock.getBuyPrice());
        dto.setCurrentPrice(stock.getCurrentPrice());
        return dto;
    }
}