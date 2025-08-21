package com.eduardoxduardo.vbank.service;

import com.eduardoxduardo.vbank.dto.ClientCreateRequestDTO;
import com.eduardoxduardo.vbank.dto.ClientResponseDTO;
import com.eduardoxduardo.vbank.dto.ClientUpdateRequestDTO;
import com.eduardoxduardo.vbank.mapper.ClientMapper;
import com.eduardoxduardo.vbank.model.entities.Client;
import com.eduardoxduardo.vbank.repository.ClientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional(readOnly = true)
    public ClientResponseDTO findById(Long id) {
        // TODO: Implement proper exception handling and custom exception
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        return ClientMapper.toDTO(client);
    }

    // TODO: Change the method from findAll to search and implement filtering and sorting functionality
    @Transactional(readOnly = true)
    public Page<ClientResponseDTO> findAll(Pageable pageable) {
        Page<Client> clientsPage = clientRepository.findAll(pageable);
        return clientsPage.map(ClientMapper::toDTO);
    }

    @Transactional
    public ClientResponseDTO update(Long id, ClientUpdateRequestDTO request) {
        // Check if the client exists
        // TODO: Implement proper exception handling and custom exceptions
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        // Update the client's fields
        // Note: None of them are required, so we can update all fields
        if (request.getName() != null && !request.getName().isEmpty()) {
            existingClient.setName(request.getName());
        }

        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            if (!request.getEmail().equals(existingClient.getEmail()) &&
                    clientRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Client with this email already exists");
            }
            existingClient.setEmail(request.getEmail());
        }

        if (request.getPhone() != null && !request.getPhone().isEmpty()) {
            existingClient.setPhone(request.getPhone());
        }

        if (request.getAddress() != null && !request.getAddress().isEmpty()) {
            existingClient.setAddress(request.getAddress());
        }

        // Save the updated client
        Client updatedClient = clientRepository.save(existingClient);
        return ClientMapper.toDTO(updatedClient);
    }

    @Transactional
    public void delete(Long id) {
        // Check if the client exists
        // TODO: Implement proper exception handling and custom exceptions
        if (!clientRepository.existsById(id)) {
            throw new RuntimeException("Client not found");
        }
        clientRepository.deleteById(id);
    }
}
