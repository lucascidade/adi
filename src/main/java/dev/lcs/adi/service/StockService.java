package dev.lcs.adi.service;

import dev.lcs.adi.controller.dto.CreateStockDTO;
import dev.lcs.adi.entity.Stock;
import dev.lcs.adi.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class StockService {
    private final StockRepository stockRepository;
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void createStock(CreateStockDTO createStockDTO) {
        //DTO - > ENTITY

        var stock = new Stock(
                createStockDTO.stockId(),
                createStockDTO.description()
        );

        stockRepository.save(stock);
    }
}
