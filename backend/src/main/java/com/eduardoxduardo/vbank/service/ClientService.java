package com.eduardoxduardo.vbank.service;

import com.eduardoxduardo.vbank.dto.client.ClientCreateRequestDTO;
import com.eduardoxduardo.vbank.dto.client.ClientResponseDTO;
import com.eduardoxduardo.vbank.dto.client.ClientSearchCriteria;
import com.eduardoxduardo.vbank.dto.client.ClientUpdateRequestDTO;
import com.eduardoxduardo.vbank.mapper.ClientMapper;
import com.eduardoxduardo.vbank.model.entities.Client;
import com.eduardoxduardo.vbank.repository.ClientRepository;
import com.eduardoxduardo.vbank.repository.specification.SpecificationBuilder;
import com.eduardoxduardo.vbank.service.exceptions.BusinessViolationException;
import com.eduardoxduardo.vbank.service.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final SpecificationBuilder<Client, ClientSearchCriteria> specificationBuilder;

    @Transactional
    public ClientResponseDTO create(ClientCreateRequestDTO request) {
        // Check if the client already exists by document or email
        if (clientRepository.existsByDocument(request.getDocument())) {
            throw new BusinessViolationException("Client with the document" + request.getDocument() + " already exists");
        }
        if (clientRepository.existsByEmail(request.getEmail())) {
            throw new BusinessViolationException("Client with the email" + request.getEmail() + " already exists");
        }

        Client client = ClientMapper.toEntity(request);
        Client savedClient = clientRepository.save(client);
        return ClientMapper.toDTO(savedClient);
    }

    @Transactional(readOnly = true)
    public ClientResponseDTO findById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client with ID: " + id + " not found"));
        return ClientMapper.toDTO(client);
    }

    @Transactional(readOnly = true)
    public Page<ClientResponseDTO> search(ClientSearchCriteria criteria, Pageable pageable) {
        Specification<Client> spec = specificationBuilder.build(criteria);

        Page<Client> clientsPage = clientRepository.findAll(spec, pageable);

        return clientsPage.map(ClientMapper::toDTO);
    }

    @Transactional
    public ClientResponseDTO update(Long id, ClientUpdateRequestDTO request) {
        // Check if the client exists
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client with ID: " + id + " not found"));

        // Update the client's fields
        // Note: None of them are required, so we can update all fields
        if (request.getName() != null && !request.getName().isEmpty()) {
            existingClient.setName(request.getName());
        }

        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            if (!request.getEmail().equals(existingClient.getEmail()) &&
                    clientRepository.existsByEmail(request.getEmail())) {
                throw new BusinessViolationException("Client with the email" + request.getEmail() + " already exists");
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
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Client with ID: " + id + " not found");
        }
        clientRepository.deleteById(id);
    }
}
