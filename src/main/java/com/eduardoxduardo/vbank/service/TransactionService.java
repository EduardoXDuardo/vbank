package com.eduardoxduardo.vbank.service;

import com.eduardoxduardo.vbank.config.RabbitMQConfig;
import com.eduardoxduardo.vbank.dto.transaction.TransactionEventDTO;
import com.eduardoxduardo.vbank.dto.transaction.TransactionRequestDTO;
import com.eduardoxduardo.vbank.dto.transaction.TransactionResponseDTO;
import com.eduardoxduardo.vbank.dto.transaction.TransactionSearchCriteria;
import com.eduardoxduardo.vbank.mapper.TransactionMapper;
import com.eduardoxduardo.vbank.model.entities.Account;
import com.eduardoxduardo.vbank.model.entities.Transaction;
import com.eduardoxduardo.vbank.model.enums.TransactionStatus;
import com.eduardoxduardo.vbank.repository.AccountRepository;
import com.eduardoxduardo.vbank.repository.TransactionRepository;
import com.eduardoxduardo.vbank.repository.specification.SpecificationBuilder;
import com.eduardoxduardo.vbank.service.exceptions.ResourceNotFoundException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final SpecificationBuilder<Transaction, TransactionSearchCriteria> specificationBuilder;
    private final RabbitTemplate rabbitTemplate;


    @Transactional
    public TransactionResponseDTO request(TransactionRequestDTO request) {

        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Account with ID: " + request.getAccountId() + " not found"));

        // Create a new transaction with PENDING status
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setStatus(TransactionStatus.PENDING); // Initial status is PENDING

        // Save the transaction to get an ID
        Transaction pendingTransaction = transactionRepository.save(transaction);

        // Send a message to RabbitMQ for asynchronous processing
        TransactionEventDTO event = new TransactionEventDTO(pendingTransaction.getId());

        // Send the event to the specified exchange with the routing key
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, event);

        // Return the pending transaction details
        return TransactionMapper.toDTO(pendingTransaction);
    }

    @Transactional(readOnly = true)
    public TransactionResponseDTO findById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction with ID: " + id + " not found"));

        return TransactionMapper.toDTO(transaction);
    }

    @Transactional(readOnly = true)
    public Page<TransactionResponseDTO> search(TransactionSearchCriteria criteria, Pageable pageable) {
        Specification<Transaction> spec = specificationBuilder.build(criteria);
        Page<Transaction> transactions = transactionRepository.findAll(spec, pageable);
        return transactions.map(TransactionMapper::toDTO);
    }
}
