package com.eduardoxduardo.vbank.controller;

import com.eduardoxduardo.vbank.dto.AccountResponseDTO;
import com.eduardoxduardo.vbank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

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
}
