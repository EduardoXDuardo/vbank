package com.eduardoxduardo.vbank.service;

import com.eduardoxduardo.vbank.dto.client.ClientCreateRequestDTO;
import com.eduardoxduardo.vbank.dto.client.ClientResponseDTO;
import com.eduardoxduardo.vbank.mapper.ClientMapper;
import com.eduardoxduardo.vbank.model.entities.Client;
import com.eduardoxduardo.vbank.repository.ClientRepository;
import com.eduardoxduardo.vbank.service.exceptions.BusinessViolationException;
import com.eduardoxduardo.vbank.service.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    // ========== Create Tests ==========

    @Test
    @DisplayName("Should throw BusinessViolationException when document already exists")
    public void create_whenDocumentAlreadyExists_ShouldThrowBusinessViolationException() {
        // Create an account
        ClientCreateRequestDTO request = new ClientCreateRequestDTO();
        request.setEmail("test@example.com");
        request.setDocument("1234567890");
        request.setName("User");
        request.setPhone("11912345678");
        request.setAddress("Some Address");

        // Mock the repository to simulate existing document
        when(clientRepository.existsByDocument(request.getDocument())).thenReturn(true);

        // Act & Assert
        BusinessViolationException exception = assertThrows(BusinessViolationException.class, () -> {
            clientService.create(request);
        });

        assertEquals("Client with the document " + request.getDocument() + " already exists", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw BusinessViolationException when email already exists")
    public void create_WhenEmailAlreadyExists_ShouldThrowBusinessViolationException() {
        // Create an account
        ClientCreateRequestDTO request = new ClientCreateRequestDTO();
        request.setEmail("test@example.com");
        request.setDocument("1234567890");
        request.setName("User");
        request.setPhone("11912345678");
        request.setAddress("Some Address");

        // Mock the repository to simulate existing email
        when(clientRepository.existsByEmail(request.getEmail())).thenReturn(true);

        // Act & Assert
        BusinessViolationException exception = assertThrows(BusinessViolationException.class, () -> {
            clientService.create(request);
        });

        assertEquals("Client with the email " + request.getEmail() + " already exists", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw BusinessViolationException when phone already exists")
    public void create_WhenPhoneAlreadyExists_ShouldThrowBusinessViolationException() {
        // Create an account
        ClientCreateRequestDTO request = new ClientCreateRequestDTO();
        request.setEmail("test@example.com");
        request.setDocument("1234567890");
        request.setName("User");
        request.setPhone("11912345678");
        request.setAddress("Some Address");

        // Mock the repository to simulate existing phone
        when(clientRepository.existsByPhone(request.getPhone())).thenReturn(true);

        // Act & Assert
        BusinessViolationException exception = assertThrows(BusinessViolationException.class, () -> {
            clientService.create(request);
        });

        assertEquals("Client with the phone " + request.getPhone() + " already exists", exception.getMessage());
    }

    @Test
    @DisplayName("Should create client when request is valid")
    public void create_WhenValidRequest_ShouldCreateClient() {
        // Create an account
        ClientCreateRequestDTO request = new ClientCreateRequestDTO();
        request.setEmail("test@example.com");
        request.setDocument("1234567890");
        request.setName("User");
        request.setPhone("11912345678");
        request.setAddress("Some Address");

        // Mock the repository to simulate non-existing document and email
        when(clientRepository.existsByDocument(request.getDocument())).thenReturn(false);
        when(clientRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(clientRepository.existsByPhone(request.getPhone())).thenReturn(false);

        // Mock the save operation to return a client with an ID
        Client savedClient = ClientMapper.toEntity(request);
        savedClient.setId(1L); // Simulate the database assigning an ID
        when(clientRepository.save(any(Client.class))).thenReturn(savedClient);

        // Act
        ClientResponseDTO response = clientService.create(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("User", response.getName());

        // Verify that the save method was called exactly once
        verify(clientRepository).save(any(Client.class));
    }

    // ========== Read Tests ==========

    @Test
    @DisplayName("Should return client when client exists")
    public void findById_whenClientExists_ShouldReturnClient() {
        // Create a client
        Client client = new Client();
        Long clientId = 1L;
        client.setId(clientId);
        client.setName("Test User");
        client.setDocument("12345678901");
        client.setEmail("test@example.com");
        client.setPhone("11987654321");
        client.setAddress("Some Address");

        when(clientRepository.findById(clientId)).thenReturn(java.util.Optional.of(client));

        // Act
        ClientResponseDTO response = clientService.findById(clientId);

        // Assert
        assertNotNull(response);
        assertEquals(clientId, response.getId());
        assertEquals("Test User", response.getName());
        assertEquals("12345678901", response.getDocument());
        assertEquals("test@example.com", response.getEmail());
        assertEquals("11987654321", response.getPhone());
        assertEquals("Some Address", response.getAddress());

        // Verify that the repository method was called
        verify(clientRepository).findById(clientId);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when client does not exist")
    public void findById_whenClientDoesNotExist_ShouldThrowResourceNotFoundException() {
        // Given
        Long clientId = 1L;

        // Mock the repository to simulate non-existing client
        when(clientRepository.findById(clientId)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
        clientService.findById(clientId);
        });

        // Verify that the repository method was called
        verify(clientRepository).findById(clientId);
    }

    public void search_whenValidCriteriaAndPageable_ShouldReturnPagedClients() {
        // TODO
    }

    public void search_whenNoClientsMatchCriteria_ShouldReturnEmptyPage() {
        // TODO
    }

    public void search_whenInvalidCriteria_ShouldThrowException() {
        // TODO
    }

    public void search_whenInvalidPageable_ShouldThrowException() {
        // TODO
    }

    // ========== Update Tests ==========

    public void update_whenClientExistsAndValidRequest_ShouldUpdateClient() {
        // TODO
    }

    public void update_whenNoFieldsToUpdate_ShouldNotChangeClient() {
        // TODO
    }

    public void update_whenClientDoesNotExist_ShouldThrowResourceNotFoundException() {
        // TODO
    }

    public void update_whenEmailIsInvalid_ShouldThrowValidationException() {
        // TODO
    }

    public void update_whenEmailAlreadyExists_ShouldThrowBusinessViolationException() {
        // TODO
    }

    public void update_whenPhoneAlreadyExists_ShouldThrowBusinessViolationException() {
        // TODO
    }

    // ========== Delete Tests ==========

    public void delete_whenClientExists_ShouldDeleteClient() {
        // TODO
    }

    public void delete_whenClientDoesNotExist_ShouldThrowResourceNotFoundException() {
        // TODO
    }
}
