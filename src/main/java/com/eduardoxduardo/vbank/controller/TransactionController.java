package com.eduardoxduardo.vbank.controller;

import com.eduardoxduardo.vbank.dto.transaction.TransactionRequestDTO;
import com.eduardoxduardo.vbank.dto.transaction.TransactionResponseDTO;
import com.eduardoxduardo.vbank.dto.transaction.TransactionSearchCriteria;
import com.eduardoxduardo.vbank.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> create(@Valid @RequestBody TransactionRequestDTO request) {
        TransactionResponseDTO transaction = transactionService.execute(request);
        return ResponseEntity.status(201).body(transaction);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> findById(@PathVariable Long id) {
        TransactionResponseDTO transaction = transactionService.findById(id);
        return ResponseEntity.ok(transaction);
    }

    @GetMapping
    public ResponseEntity<Page<TransactionResponseDTO>> search(TransactionSearchCriteria criteria, Pageable pageable) {
        Page<TransactionResponseDTO> transactions = transactionService.search(criteria, pageable);
        return ResponseEntity.ok(transactions);
    }
}
