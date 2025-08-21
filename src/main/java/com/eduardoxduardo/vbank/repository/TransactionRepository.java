package com.eduardoxduardo.vbank.repository;

import com.eduardoxduardo.vbank.dto.TransactionResponseDTO;
import com.eduardoxduardo.vbank.model.entities.Transaction;
import com.eduardoxduardo.vbank.model.enums.TransactionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    boolean existsByAccountIdAndStatus(Long accountId, TransactionStatus status);
    Page<Transaction> findByAccountId(Long accountId, Pageable pageable);
}
