package com.eduardoxduardo.vbank.controller;

import com.eduardoxduardo.vbank.dto.client.ClientCreateRequestDTO;
import com.eduardoxduardo.vbank.dto.client.ClientResponseDTO;
import com.eduardoxduardo.vbank.dto.client.ClientSearchCriteria;
import com.eduardoxduardo.vbank.dto.client.ClientUpdateRequestDTO;
import com.eduardoxduardo.vbank.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    @Operation(summary = "Create a new client")
    @PostMapping
    public ResponseEntity<ClientResponseDTO> create(@Valid @RequestBody ClientCreateRequestDTO request) {
        return ResponseEntity.status(201).body(clientService.create(request));
    }

    @Operation(summary = "Get client by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> findById(@PathVariable Long id) {
        ClientResponseDTO clientResponse = clientService.findById(id);
        return ResponseEntity.ok(clientResponse);
    }

    @Operation(summary = "Search clients with optional filters and pagination")
    @GetMapping
    public ResponseEntity<Page<ClientResponseDTO>> search(@ParameterObject ClientSearchCriteria criteria,@ParameterObject Pageable pageable) {
        Page<ClientResponseDTO> clientsPage = clientService.search(criteria, pageable);
        return ResponseEntity.ok(clientsPage);
    }

    @Operation(summary = "Update client by ID")
    @PatchMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ClientUpdateRequestDTO request) {
        ClientResponseDTO updatedClient = clientService.update(id, request);
        return ResponseEntity.ok(updatedClient);
    }

    @Operation(summary = "Delete client by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
