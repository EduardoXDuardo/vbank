package com.eduardoxduardo.vbank.controller;

import com.eduardoxduardo.vbank.dto.account.AccountCreateRequestDTO;
import com.eduardoxduardo.vbank.dto.account.AccountResponseDTO;
import com.eduardoxduardo.vbank.dto.account.AccountSearchCriteria;
import com.eduardoxduardo.vbank.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Accounts", description = "Endpoints for managing accounts")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "Create a new account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data, e.g., missing required fields"),
            @ApiResponse(responseCode = "404", description = "Client not found for the provided client ID"),
    })
    @PostMapping
    public ResponseEntity<AccountResponseDTO> create(@RequestBody AccountCreateRequestDTO request) {
        AccountResponseDTO accountResponse = accountService.create(request);
        return ResponseEntity.status(201).body(accountResponse);
    }

    @Operation(summary = "Get account by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account found"),
            @ApiResponse(responseCode = "404", description = "Account not found for the provided ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> findById(@PathVariable Long id) {
        AccountResponseDTO accountResponse = accountService.findById(id);
        return ResponseEntity.ok(accountResponse);
    }

    @Operation(summary = "Search accounts with optional filters and pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accounts found"),
            @ApiResponse(responseCode = "400", description = "Invalid search criteria or pagination parameters")
    })
    @GetMapping
    public ResponseEntity<Page<AccountResponseDTO>> search(@ParameterObject AccountSearchCriteria criteria, @ParameterObject Pageable pageable) {
        Page<AccountResponseDTO> accountsPage = accountService.search(criteria, pageable);
        return ResponseEntity.ok(accountsPage);
    }

    @Operation(summary = "Delete account by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Account deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Account have nonzero balance or has pending transactions, and cannot be deleted"),
            @ApiResponse(responseCode = "404", description = "Account not found for the provided ID")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
