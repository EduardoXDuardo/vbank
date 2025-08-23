package com.eduardoxduardo.vbank.service;

import com.eduardoxduardo.vbank.config.RabbitMQConfig;
import com.eduardoxduardo.vbank.dto.transaction.TransactionEventDTO;
import com.eduardoxduardo.vbank.model.entities.Account;
import com.eduardoxduardo.vbank.model.entities.Transaction;
import com.eduardoxduardo.vbank.model.enums.TransactionStatus;
import com.eduardoxduardo.vbank.repository.AccountRepository;
import com.eduardoxduardo.vbank.repository.TransactionRepository;
import com.eduardoxduardo.vbank.service.exceptions.BusinessViolationException;
import com.eduardoxduardo.vbank.service.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionConsumer {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    @Transactional
    public void processTransaction(TransactionEventDTO event) {
        Long transactionId = event.getTransactionId();
        log.info("Processing transaction with ID: {}", transactionId);

        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction with ID: " + transactionId + " not found"));

        Account account = transaction.getAccount();

        try {
            switch (transaction.getType()) {
                case DEPOSIT:
                    processDeposit(transaction, account);
                    break;
                case WITHDRAWAL:
                    processWithdrawal(transaction, account);
                    break;
                default:
                    // This should never happen due to enum constraints, but just in case :)
                    throw new BusinessViolationException("Transaction with ID: " + transactionId + " has an unknown type");
            }
        } catch (BusinessViolationException e) {
            // If a business rule is violated, mark the transaction as FAILED
            transaction.setStatus(TransactionStatus.FAILED);
            log.error("Business violation for transaction with ID {}: {}", transactionId, e.getMessage());
        } catch (Exception e) {
            // Catch any other unexpected exceptions, just in case too :)
            transaction.setStatus(TransactionStatus.FAILED);
            log.error("Error processing transaction with ID: {}", transactionId, e);
        }

        transactionRepository.save(transaction);
    }

    private void processDeposit(Transaction transaction, Account account) {
        // Validating if deposit amount is positive
        if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessViolationException("Deposit amount cannot be zero or negative");
        }

        // Performing the deposit
        account.setBalance(account.getBalance().add(transaction.getAmount()));

        log.info("Successfully deposited {} to account ID {}. New balance: {}", transaction.getAmount(), account.getId(), account.getBalance());
        
        // Marking the transaction as COMPLETED and saving the account
        transaction.setStatus(TransactionStatus.COMPLETED);
        accountRepository.save(account);
    }

    private void processWithdrawal(Transaction transaction, Account account) {
        // Validating if withdrawal amount is positive
        if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessViolationException("Withdrawal amount cannot be zero or negative");
        }

        // Check for sufficient funds to avoid negative balance
        if (account.getBalance().compareTo(transaction.getAmount()) < 0) {
            throw new BusinessViolationException("Insufficient funds for withdrawal");
        }
        
        // Performing the withdrawal
        account.setBalance(account.getBalance().subtract(transaction.getAmount()));

        log.info("Successfully withdrew {} from account ID {}. New balance: {}", transaction.getAmount(), account.getId(), account.getBalance());

        // Marking the transaction as COMPLETED and saving the account
        transaction.setStatus(TransactionStatus.COMPLETED);
        accountRepository.save(account);
    }
}
