package com.eduardoxduardo.vbank.service;

import com.eduardoxduardo.vbank.dto.TransactionRequestDTO;
import com.eduardoxduardo.vbank.dto.TransactionResponseDTO;
import com.eduardoxduardo.vbank.mapper.TransactionMapper;
import com.eduardoxduardo.vbank.model.entities.Account;
import com.eduardoxduardo.vbank.model.entities.Transaction;
import com.eduardoxduardo.vbank.model.enums.TransactionStatus;
import com.eduardoxduardo.vbank.model.enums.TransactionType;
import com.eduardoxduardo.vbank.repository.AccountRepository;
import com.eduardoxduardo.vbank.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;


    @Transactional
    public TransactionResponseDTO execute(TransactionRequestDTO request) {
        /*
            TODO: REFACTOR FOR ASYNCHRONOUS PROCESSING (PRODUCTION ARCHITECTURE) using a message queue (like RabbitMQ, SQS, or Kafka)

            The flow would be:
            1.  PRODUCER (This method):
                -   Validate the incoming request DTO.
                -   Create and save a Transaction entity with an initial 'PENDING' status.
                -   Publish a message/event with the transaction details to a queue.
                -   Immediately return an HTTP 202 Accepted response to the client.

            2.  CONSUMER (A separate @RabbitListener component):
                -   A background worker would consume the message from the queue in a separate transaction.
                -   Fetch the PENDING transaction from the database.
                -   Execute the core business logic (balance checks, updating the account balance).

            3.  FINAL STATUS UPDATE:
                -   If the processing is successful, the consumer updates the transaction status to 'COMPLETED'.
                -   If it fails (e.g., insufficient funds), it updates the status to 'FAILED' and could trigger a notification.
        */

        // --- Current Synchronous Implementation ---

        // TODO: Implement proper exception handling and custom exceptions
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Validate transaction type and amount
        if (request.getType() == TransactionType.DEPOSIT) {
            processDeposit(account, request.getAmount());
        } else if (request.getType() == TransactionType.WITHDRAWAL) {
            processWithdrawal(account, request.getAmount());
        }

        // If reached here, the transaction has been processed successfully
        // Saves the transaction as completed and updates the account balance
        accountRepository.save(account);
        Transaction transaction = recordTransaction(account, request.getType(), request.getAmount());

        return TransactionMapper.toDTO(transaction);
    }

    @Transactional(readOnly = true)
    public TransactionResponseDTO findById(Long id) {
        // TODO: Implement proper exception handling and custom exceptions
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        return TransactionMapper.toDTO(transaction);
    }

    @Transactional(readOnly = true)
    public Page<TransactionResponseDTO> findAll(Pageable pageable) {
        Page<Transaction> transactions = transactionRepository.findAll(pageable);
        return transactions.map(TransactionMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public Page<TransactionResponseDTO> findByAccountId(Long accountId, Pageable pageable) {
        Page<Transaction> transactions = transactionRepository.findByAccountId(accountId, pageable);
        return transactions.map(TransactionMapper::toDTO);
    }

    private void processDeposit(Account account, BigDecimal amount) {
        // TODO: Implement proper exception handling and custom exceptions
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("The deposit amount must be positive.");
        }
        account.setBalance(account.getBalance().add(amount));
    }

    private void processWithdrawal(Account account, BigDecimal amount) {
        // TODO: Implement proper exception handling and custom exceptions
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("The withdrawal amount must be positive.");
        }
        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("The withdrawal amount exceeds the account balance.");
        }
        account.setBalance(account.getBalance().subtract(amount));
    }

    private Transaction recordTransaction(Account account, TransactionType type, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setType(type);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setStatus(TransactionStatus.COMPLETED);

        return transactionRepository.save(transaction);
    }
}
