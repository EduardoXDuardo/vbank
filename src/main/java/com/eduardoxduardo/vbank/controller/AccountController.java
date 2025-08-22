package com.eduardoxduardo.vbank.controller;

import com.eduardoxduardo.vbank.dto.account.AccountCreateRequestDTO;
import com.eduardoxduardo.vbank.dto.account.AccountResponseDTO;
import com.eduardoxduardo.vbank.dto.account.AccountSearchCriteria;
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
    public ResponseEntity<Page<AccountResponseDTO>> search(AccountSearchCriteria criteria, Pageable pageable) {
        Page<AccountResponseDTO> accountsPage = accountService.search(criteria, pageable);
        return ResponseEntity.ok(accountsPage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
