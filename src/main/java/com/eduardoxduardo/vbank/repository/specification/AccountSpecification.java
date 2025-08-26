package com.eduardoxduardo.vbank.repository.specification;

import com.eduardoxduardo.vbank.dto.account.AccountSearchCriteria;
import com.eduardoxduardo.vbank.model.entities.Account;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AccountSpecification implements SpecificationBuilder<Account, AccountSearchCriteria>{
    @Override
    public Specification<Account> build(AccountSearchCriteria criteria) {
        return (root, query, cb) -> {
            var predicates = cb.conjunction();

            if (criteria.getId() != null) {
                predicates = cb.and(predicates, cb.equal(root.get("id"), criteria.getId()));
            }

            if (criteria.getAccountNumber() != null && !criteria.getAccountNumber().isEmpty()) {
                predicates = cb.and(predicates, cb.equal(root.get("accountNumber"), criteria.getAccountNumber()));
            }

            if (criteria.getClientId() != null) {
                predicates = cb.and(predicates, cb.equal(root.get("client").get("id"), criteria.getClientId()));
            }

            return predicates;
        };
    }
}
