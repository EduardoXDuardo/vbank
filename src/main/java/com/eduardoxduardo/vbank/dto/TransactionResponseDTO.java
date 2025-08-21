package com.eduardoxduardo.vbank.dto;

import com.eduardoxduardo.vbank.model.enums.TransactionStatus;
import com.eduardoxduardo.vbank.model.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponseDTO {
    private Long id;
    private TransactionType transactionType;
    private AccountResponseDTO account;
    private BigDecimal amount;
    private TransactionStatus status;
    private LocalDateTime timestamp;
}
