package com.eduardoxduardo.vbank.dto.client;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientUpdateRequestDTO {

    private String name;

    @Email(message = "Email should be valid")
    private String email;

    private String phone;

    private String address;
}
