package dev.lcs.adi.repository;

import dev.lcs.adi.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StockRepository extends JpaRepository <Stock, String> {
}
