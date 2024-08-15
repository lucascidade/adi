package dev.lcs.adi.service;

import dev.lcs.adi.controller.dto.AccountResponseDTO;
import dev.lcs.adi.controller.dto.AccountStockResponseDTO;
import dev.lcs.adi.controller.dto.AssociateAccountDTO;
import dev.lcs.adi.entity.AccountStock;
import dev.lcs.adi.entity.AccountStockId;
import dev.lcs.adi.repository.AccountRepository;
import dev.lcs.adi.repository.AccountStockRepository;
import dev.lcs.adi.repository.StockRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService{

    private final AccountRepository accountRepository;
    private final StockRepository stockRepository;
    private final AccountStockRepository accountStockRepository;


    public AccountService(AccountRepository accountRepository, StockRepository sockRepository, StockRepository stockRepository, AccountStockRepository accountStockRepository) {
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.accountStockRepository = accountStockRepository;
    }

    public void asssociateStock(String accountId, AssociateAccountDTO associateAccountDTO) {
        var account = accountRepository.findById(UUID.fromString(accountId)).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var stock = stockRepository.findById(associateAccountDTO.stockId()).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var id = new AccountStockId(account.getAccountId(), stock.getStockId());

        var entity = new AccountStock(
                id,
                account,
                stock,
                associateAccountDTO.quantity()
        );
        accountStockRepository.save(entity);
    }



    public List<AccountStockResponseDTO> listStocks(String accountId) {
        var account = accountRepository.findById(UUID.fromString(accountId)).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return account.getAccountStocks()
                .stream()
                .map(as ->
                        new AccountStockResponseDTO(as.getStock().getStockId(), as.getQuantity(), 0.0)).toList();
    }
}
