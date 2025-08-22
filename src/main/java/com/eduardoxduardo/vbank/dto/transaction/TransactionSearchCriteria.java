package com.eduardoxduardo.vbank.dto.transaction;

import com.eduardoxduardo.vbank.model.enums.TransactionStatus;
import com.eduardoxduardo.vbank.model.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionSearchCriteria {
    private Long id;
    private Long accountId;
    private TransactionType type;
    private TransactionStatus status;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private LocalDateTime afterDate;
    private LocalDateTime beforeDate;
}
