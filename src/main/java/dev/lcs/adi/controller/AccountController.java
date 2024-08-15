package dev.lcs.adi.controller;

import dev.lcs.adi.controller.dto.AccountStockResponseDTO;
import dev.lcs.adi.controller.dto.AssociateAccountDTO;
import dev.lcs.adi.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    private final AccountService accountService;
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/{accountId}/stocks")
    public ResponseEntity<Void> associateStrock(@PathVariable("accountId") String accountId,
                                                @RequestBody AssociateAccountDTO associateAccountDTO) {

        accountService.asssociateStock(accountId, associateAccountDTO);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/{accountId}/stocks")
    public ResponseEntity<List<AccountStockResponseDTO>> listStocks(@PathVariable("accountId") String accountId) {

        var stocks = accountService.listStocks(accountId);
        return ResponseEntity.ok(stocks);
    }
}
