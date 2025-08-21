package com.eduardoxduardo.vbank.service;

import com.eduardoxduardo.vbank.dto.ClientCreateRequestDTO;
import com.eduardoxduardo.vbank.dto.ClientResponseDTO;
import com.eduardoxduardo.vbank.mapper.ClientMapper;
import com.eduardoxduardo.vbank.model.entities.Client;
import com.eduardoxduardo.vbank.repository.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    @Transactional
    public ClientResponseDTO create(ClientCreateRequestDTO request) {
        // Check if the client already exists by document or email
        // TODO: Implement proper exception handling and custom exceptions
        if (clientRepository.existsByDocument(request.getDocument())) {
            throw new RuntimeException("Client with this document already exists");
        }
        if (clientRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Client with this email already exists");
        }

        Client client = ClientMapper.toEntity(request);
        Client savedClient = clientRepository.save(client);
        return ClientMapper.toDTO(savedClient);
    }

    @Transactional
    public ClientResponseDTO findById(Long id) {
        // TODO: Implement proper exception handling and custom exception
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        return ClientMapper.toDTO(client);
    }
}
