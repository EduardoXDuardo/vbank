package com.eduardoxduardo.vbank.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientCreateRequestDTO {

    @Schema(description = "Document must be either 11 digits (CPF) or 14 digits (CNPJ)", example = "12345678901 or 12345678000199")
    @NotBlank(message = "Document cannot be blank")
    @Pattern(regexp = "(^\\d{11}$)|(^\\d{14}$)", message = "Document must be either 11 digits (CPF) or 14 digits (CNPJ)")
    private String document;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Schema(example = "example@example.com")
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @Schema(description = "Phone number must be not formatted, only digits", example = "11987654321")
    @NotBlank(message = "Phone cannot be blank")
    private String phone;

    @NotBlank(message = "Address cannot be blank")
    private String address;
}
