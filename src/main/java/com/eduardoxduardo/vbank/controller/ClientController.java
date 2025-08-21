package com.eduardoxduardo.vbank.controller;

import com.eduardoxduardo.vbank.dto.ClientCreateRequestDTO;
import com.eduardoxduardo.vbank.dto.ClientResponseDTO;
import com.eduardoxduardo.vbank.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
}
