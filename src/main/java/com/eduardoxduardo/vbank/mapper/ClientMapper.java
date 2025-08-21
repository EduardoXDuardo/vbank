package com.eduardoxduardo.vbank.mapper;

import com.eduardoxduardo.vbank.dto.ClientResponseDTO;
import com.eduardoxduardo.vbank.model.entities.Client;

public class ClientMapper {
    public static ClientResponseDTO toDTO(Client client) {
        return new ClientResponseDTO(
                client.getId(),
                client.getDocument(),
                client.getName(),
                client.getEmail(),
                client.getPhone(),
                client.getAddress()
        );
    }
}
