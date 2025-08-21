package com.eduardoxduardo.vbank.controller;

import com.eduardoxduardo.vbank.dto.TransactionRequestDTO;
import com.eduardoxduardo.vbank.dto.TransactionResponseDTO;
import com.eduardoxduardo.vbank.model.entities.Transaction;
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
    public ResponseEntity<Page<TransactionResponseDTO>> findAll(Pageable pageable) {
        Page<TransactionResponseDTO> transactions = transactionService.findAll(pageable);
        return ResponseEntity.ok(transactions);
    }
}
