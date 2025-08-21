package com.eduardoxduardo.vbank.controller;

import com.eduardoxduardo.vbank.dto.ClientCreateRequestDTO;
import com.eduardoxduardo.vbank.dto.ClientResponseDTO;
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

    // TODO: Change the method from findAll to search and implement filtering and sorting functionality
    @GetMapping
    public ResponseEntity<Page<ClientResponseDTO>> findAll(Pageable pageable) {
        Page<ClientResponseDTO> clientsPage = clientService.findAll(pageable);
        return ResponseEntity.ok(clientsPage);
    }
}
