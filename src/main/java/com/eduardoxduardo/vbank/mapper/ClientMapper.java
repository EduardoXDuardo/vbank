package com.eduardoxduardo.vbank.mapper;

import com.eduardoxduardo.vbank.dto.ClientCreateRequestDTO;
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

    public static Client toEntity(ClientCreateRequestDTO dto) {
        Client entity = new Client();
        entity.setDocument(dto.getDocument());
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setAddress(dto.getAddress());
        return entity;
    }
}
