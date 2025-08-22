package com.eduardoxduardo.vbank.service;

import com.eduardoxduardo.vbank.dto.account.AccountCreateRequestDTO;
import com.eduardoxduardo.vbank.dto.account.AccountResponseDTO;
import com.eduardoxduardo.vbank.mapper.AccountMapper;
import com.eduardoxduardo.vbank.model.entities.Account;
import com.eduardoxduardo.vbank.model.entities.Client;
import com.eduardoxduardo.vbank.model.enums.TransactionStatus;
import com.eduardoxduardo.vbank.repository.AccountRepository;
import com.eduardoxduardo.vbank.repository.ClientRepository;
import com.eduardoxduardo.vbank.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public AccountResponseDTO create(AccountCreateRequestDTO request) {
        // Check if the client exists and is valid
        // TODO: Implement proper exception handling and custom exceptions
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        // For now, a client can only have one account
        // TODO: If implementing multiple accounts per client (account types), remove this check
        if (accountRepository.existsByClientId(request.getClientId())) {
            throw new RuntimeException("Client already has an account");
        }

        // Create a new account
        // Note: The default balance is set to zero in the Account entity
        Account account = new Account();
        account.setClient(client);

        // Generates the account id
        account = accountRepository.save(account);

        // Uses the account id to generate the account number
        account.setAccountNumber(generateAccountNumber(account.getId()));

        // Save the account with the generated account number
        account = accountRepository.save(account);

        return AccountMapper.toDTO(account);
    }


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

    @Transactional
    public void delete(Long id) {
        // Check if the account exists by ID
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Delete the account only if the balance is zero
        if (!Objects.equals(account.getBalance(), BigDecimal.ZERO)) {
            throw new RuntimeException("Cannot delete account with non-zero balance");
        }

        // Do not delete the account if it has pending transactions associated with it
        if (transactionRepository.existsByAccountIdAndStatus(id, TransactionStatus.PENDING)) {
            throw new RuntimeException("Cannot delete account with pending transactions");
        }

        accountRepository.deleteById(id);
    }

    private String generateAccountNumber(Long clientId) {
        // TODO: If implementing multiple accounts per client, change the logic to generate account numbers based on the account type
        return String.format("%04d-1", clientId);
    }
}
