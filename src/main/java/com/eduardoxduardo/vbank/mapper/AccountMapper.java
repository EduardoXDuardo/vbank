package com.eduardoxduardo.vbank.mapper;

import com.eduardoxduardo.vbank.dto.AccountResponseDTO;
import com.eduardoxduardo.vbank.model.entities.Account;

public class AccountMapper {
    public static AccountResponseDTO toDTO(Account account) {
        return new AccountResponseDTO(
                account.getId(),
                account.getAccountNumber(),
                account.getBalance(),
                ClientMapper.toDTO(account.getClient())
        );
    }
}
