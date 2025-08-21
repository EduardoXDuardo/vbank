package com.eduardoxduardo.vbank.service;

import com.eduardoxduardo.vbank.dto.AccountResponseDTO;
import com.eduardoxduardo.vbank.mapper.AccountMapper;
import com.eduardoxduardo.vbank.model.entities.Account;
import com.eduardoxduardo.vbank.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional(readOnly = true)
    public AccountResponseDTO findById(Long id) {
        // Check if the account exists by ID
        // TODO: Implement proper exception handling and custom exceptions
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return AccountMapper.toDTO(account);
    }

    // TODO: Change the method from findAll to search and implement filtering and sorting functionality
    @Transactional(readOnly = true)
    public Page<AccountResponseDTO> findAll(Pageable pageable) {
        Page<Account> accountsPage = accountRepository.findAll(pageable);
        return accountsPage.map(AccountMapper::toDTO);
    }
}
