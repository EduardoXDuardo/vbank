package com.eduardoxduardo.vbank.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(example = "example@example.com")
    @Email(message = "Email should be valid")
    private String email;

    @Schema(description = "Phone number must be not formatted, only digits", example = "11987654321")
    private String phone;

    private String address;
}
