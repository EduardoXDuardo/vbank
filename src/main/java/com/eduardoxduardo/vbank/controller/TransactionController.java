package com.eduardoxduardo.vbank.controller;

import com.eduardoxduardo.vbank.dto.TransactionRequestDTO;
import com.eduardoxduardo.vbank.dto.TransactionResponseDTO;
import com.eduardoxduardo.vbank.model.entities.Transaction;
import com.eduardoxduardo.vbank.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
