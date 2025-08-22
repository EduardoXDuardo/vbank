package com.eduardoxduardo.vbank.controller;

import com.eduardoxduardo.vbank.dto.account.AccountCreateRequestDTO;
import com.eduardoxduardo.vbank.dto.account.AccountResponseDTO;
import com.eduardoxduardo.vbank.dto.transaction.TransactionResponseDTO;
import com.eduardoxduardo.vbank.service.AccountService;
import com.eduardoxduardo.vbank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<AccountResponseDTO> create(@RequestBody AccountCreateRequestDTO request) {
        AccountResponseDTO accountResponse = accountService.create(request);
        return ResponseEntity.status(201).body(accountResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> findById(@PathVariable Long id) {
        AccountResponseDTO accountResponse = accountService.findById(id);
        return ResponseEntity.ok(accountResponse);
    }

    @GetMapping
    public ResponseEntity<Page<AccountResponseDTO>> findAll(Pageable pageable) {
        Page<AccountResponseDTO> accountsPage = accountService.findAll(pageable);
        return ResponseEntity.ok(accountsPage);
    }

    @GetMapping("{id}/transactions")
    public ResponseEntity<Page<TransactionResponseDTO>> findTransactionsByAccountId(@PathVariable Long id, Pageable pageable) {
        Page<TransactionResponseDTO> transactionsPage = transactionService.findByAccountId(id, pageable);
        return ResponseEntity.ok(transactionsPage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
