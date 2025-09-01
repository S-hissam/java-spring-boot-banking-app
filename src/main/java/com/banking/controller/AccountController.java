package com.banking.controller;

import com.banking.dto.*;
import com.banking.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        AccountDTO account = accountService.createAccount(request);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable Long id) {
        AccountDTO account = accountService.getAccount(id);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/number/{accountNumber}")
    public ResponseEntity<AccountDTO> getAccountByNumber(@PathVariable String accountNumber) {
        AccountDTO account = accountService.getAccountByNumber(accountNumber);
        return ResponseEntity.ok(account);
    }

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        List<AccountDTO> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/active")
    public ResponseEntity<List<AccountDTO>> getActiveAccounts() {
        List<AccountDTO> accounts = accountService.getActiveAccounts();
        return ResponseEntity.ok(accounts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable Long id,
                                                    @Valid @RequestBody UpdateAccountRequest request) {
        AccountDTO account = accountService.updateAccount(id, request);
        return ResponseEntity.ok(account);
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<TransactionDTO> deposit(@PathVariable Long id,
                                                  @RequestParam BigDecimal amount,
                                                  @RequestParam(required = false) String description) {
        TransactionDTO transaction = accountService.deposit(id, amount, description);
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<TransactionDTO> withdraw(@PathVariable Long id,
                                                   @RequestParam BigDecimal amount,
                                                   @RequestParam(required = false) String description) {
        TransactionDTO transaction = accountService.withdraw(id, amount, description);
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransferResponseDTO> transfer(@Valid @RequestBody TransferRequest request) {
        TransferResponseDTO response = accountService.transfer(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<BalanceDTO> getBalance(@PathVariable Long id) {
        BalanceDTO balance = accountService.getBalance(id);
        return ResponseEntity.ok(balance);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateAccount(@PathVariable Long id) {
        accountService.deactivateAccount(id);
        return ResponseEntity.noContent().build();
    }
}