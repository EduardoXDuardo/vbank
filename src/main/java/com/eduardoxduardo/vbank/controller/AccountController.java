package com.eduardoxduardo.vbank.controller;

import com.eduardoxduardo.vbank.dto.account.AccountCreateRequestDTO;
import com.eduardoxduardo.vbank.dto.account.AccountResponseDTO;
import com.eduardoxduardo.vbank.dto.account.AccountSearchCriteria;
import com.eduardoxduardo.vbank.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "Create a new account")
    @PostMapping
    public ResponseEntity<AccountResponseDTO> create(@RequestBody AccountCreateRequestDTO request) {
        AccountResponseDTO accountResponse = accountService.create(request);
        return ResponseEntity.status(201).body(accountResponse);
    }

    @Operation(summary = "Get account by ID")
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> findById(@PathVariable Long id) {
        AccountResponseDTO accountResponse = accountService.findById(id);
        return ResponseEntity.ok(accountResponse);
    }

    @Operation(summary = "Search accounts with optional filters and pagination")
    @GetMapping
    public ResponseEntity<Page<AccountResponseDTO>> search(@ParameterObject AccountSearchCriteria criteria, @ParameterObject Pageable pageable) {
        Page<AccountResponseDTO> accountsPage = accountService.search(criteria, pageable);
        return ResponseEntity.ok(accountsPage);
    }

    @Operation(summary = "Delete account by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
