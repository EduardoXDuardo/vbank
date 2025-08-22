package com.eduardoxduardo.vbank.dto.account;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreateRequestDTO {

    @NotNull(message = "Client ID cannot be null")
    private Long clientId;

    // TODO: Implement account type
}
