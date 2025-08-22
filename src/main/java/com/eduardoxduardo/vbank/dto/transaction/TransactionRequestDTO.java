package com.eduardoxduardo.vbank.dto.transaction;

import com.eduardoxduardo.vbank.model.enums.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequestDTO {

    @NotNull
    private Long accountId;

    @NotNull
    private TransactionType type;

    @NotNull
    @Positive
    private BigDecimal amount;
}
