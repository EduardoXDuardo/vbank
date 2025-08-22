package com.eduardoxduardo.vbank.controller;

import com.eduardoxduardo.vbank.dto.client.ClientCreateRequestDTO;
import com.eduardoxduardo.vbank.dto.client.ClientResponseDTO;
import com.eduardoxduardo.vbank.dto.client.ClientSearchCriteria;
import com.eduardoxduardo.vbank.dto.client.ClientUpdateRequestDTO;
import com.eduardoxduardo.vbank.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientResponseDTO> create(@Valid @RequestBody ClientCreateRequestDTO request) {
        return ResponseEntity.status(201).body(clientService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> findById(@PathVariable Long id) {
        ClientResponseDTO clientResponse = clientService.findById(id);
        return ResponseEntity.ok(clientResponse);
    }

    @GetMapping
    public ResponseEntity<Page<ClientResponseDTO>> search(ClientSearchCriteria criteria, Pageable pageable) {
        Page<ClientResponseDTO> clientsPage = clientService.search(criteria, pageable);
        return ResponseEntity.ok(clientsPage);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ClientUpdateRequestDTO request) {
        ClientResponseDTO updatedClient = clientService.update(id, request);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
