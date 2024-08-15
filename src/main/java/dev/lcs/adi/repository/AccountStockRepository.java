package dev.lcs.adi.repository;

import dev.lcs.adi.entity.AccountStock;
import dev.lcs.adi.entity.AccountStockId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {
}
