package com.eduardoxduardo.vbank.repository;

import com.eduardoxduardo.vbank.model.entities.Transaction;
import com.eduardoxduardo.vbank.model.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {
    boolean existsByAccountIdAndStatus(Long accountId, TransactionStatus status);
}
