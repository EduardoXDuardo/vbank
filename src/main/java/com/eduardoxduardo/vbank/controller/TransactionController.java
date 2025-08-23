package com.eduardoxduardo.vbank.controller;

import com.eduardoxduardo.vbank.dto.transaction.TransactionRequestDTO;
import com.eduardoxduardo.vbank.dto.transaction.TransactionResponseDTO;
import com.eduardoxduardo.vbank.dto.transaction.TransactionSearchCriteria;
import com.eduardoxduardo.vbank.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Transactions", description = "Endpoints for managing transactions")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Operation(summary = "Create a new transaction (deposit or withdrawal)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Transaction request accepted for processing"),
            @ApiResponse(responseCode = "400", description = "Invalid transaction request data"),
            @ApiResponse(responseCode = "404", description = "Account not found for the provided account ID")
    })
    @PostMapping
    public ResponseEntity<TransactionResponseDTO> create(@Valid @RequestBody TransactionRequestDTO request) {
        TransactionResponseDTO transaction = transactionService.request(request);
        // Using 202 Accepted to indicate that the request has been accepted for processing, but maybe the processing is not complete.
        return ResponseEntity.status(202).body(transaction);
    }

    @Operation(summary = "Get transaction by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction found"),
            @ApiResponse(responseCode = "404", description = "Transaction not found for the provided ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> findById(@PathVariable Long id) {
        TransactionResponseDTO transaction = transactionService.findById(id);
        return ResponseEntity.ok(transaction);
    }

    @Operation(summary = "Search transactions with optional filters and pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transactions found"),
            @ApiResponse(responseCode = "400", description = "Invalid search criteria or pagination parameters")
    })
    @GetMapping
    public ResponseEntity<Page<TransactionResponseDTO>> search(@ParameterObject TransactionSearchCriteria criteria, @ParameterObject Pageable pageable) {
        Page<TransactionResponseDTO> transactions = transactionService.search(criteria, pageable);
        return ResponseEntity.ok(transactions);
    }
}
