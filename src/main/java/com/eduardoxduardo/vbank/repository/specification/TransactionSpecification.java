package com.eduardoxduardo.vbank.repository.specification;

import com.eduardoxduardo.vbank.dto.transaction.TransactionSearchCriteria;
import com.eduardoxduardo.vbank.model.entities.Transaction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TransactionSpecification implements SpecificationBuilder<Transaction, TransactionSearchCriteria> {
    @Override
    public Specification<Transaction> build(TransactionSearchCriteria criteria) {
        return (root, query, cb) -> {
            var predicates = cb.conjunction();

            if (criteria.getId() != null) {
                predicates = cb.and(predicates, cb.equal(root.get("id"), criteria.getId()));
            }
            if (criteria.getAccountId() != null) {
                predicates = cb.and(predicates, cb.equal(root.get("account").get("id"), criteria.getAccountId()));
            }
            if (criteria.getType() != null) {
                predicates = cb.and(predicates, cb.equal(root.get("type"), criteria.getType()));
            }
            if (criteria.getStatus() != null) {
                predicates = cb.and(predicates, cb.equal(root.get("status"), criteria.getStatus()));
            }
            if (criteria.getMinAmount() != null) {
                predicates = cb.and(predicates, cb.greaterThanOrEqualTo(root.get("amount"), criteria.getMinAmount()));
            }
            if (criteria.getMaxAmount() != null) {
                predicates = cb.and(predicates, cb.lessThanOrEqualTo(root.get("amount"), criteria.getMaxAmount()));
            }
            if (criteria.getAfterDate() != null) {
                predicates = cb.and(predicates, cb.greaterThanOrEqualTo(root.get("timestamp"), criteria.getAfterDate()));
            }
            if (criteria.getBeforeDate() != null) {
                predicates = cb.and(predicates, cb.lessThanOrEqualTo(root.get("timestamp"), criteria.getBeforeDate()));
            }

            return predicates;
        };
    }
}
