package com.eduardoxduardo.vbank.mapper;

import com.eduardoxduardo.vbank.dto.TransactionResponseDTO;
import com.eduardoxduardo.vbank.model.entities.Transaction;

public class TransactionMapper {

    public static TransactionResponseDTO toDTO(Transaction transaction) {
        return new TransactionResponseDTO(
                transaction.getId(),
                transaction.getType(),
                AccountMapper.toDTO(transaction.getAccount()),
                transaction.getAmount(),
                transaction.getStatus(),
                transaction.getTimestamp()
        );
    }
}
