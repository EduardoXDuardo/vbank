package com.eduardoxduardo.vbank.dto.transaction;

import com.eduardoxduardo.vbank.model.enums.TransactionStatus;
import com.eduardoxduardo.vbank.model.enums.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "2025-01-01T00:00:00")
    private LocalDateTime afterDate;
    @Schema(example = "2025-12-31T23:59:59")
    private LocalDateTime beforeDate;
}
