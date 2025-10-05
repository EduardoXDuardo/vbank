package com.eduardoxduardo.vbank.repository.specification;

import com.eduardoxduardo.vbank.dto.client.ClientSearchCriteria;
import com.eduardoxduardo.vbank.model.entities.Client;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ClientSpecification implements SpecificationBuilder<Client, ClientSearchCriteria> {
    @Override
    public Specification<Client> build(ClientSearchCriteria criteria) {
        return (root, query, criteriaBuilder) -> {
            var predicates = criteriaBuilder.conjunction();

            if (criteria.getId() != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.equal(root.get("id"), criteria.getId()));
            }

            if (criteria.getDocument() != null && !criteria.getDocument().isBlank()) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.equal(root.get("document"), criteria.getDocument()));
            }

            if (criteria.getName() != null && !criteria.getName().isBlank()) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + criteria.getName().toLowerCase() + "%"));
            }

            if (criteria.getEmail() != null && !criteria.getEmail().isBlank()) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + criteria.getEmail().toLowerCase() + "%"));
            }

            if (criteria.getPhone() != null && !criteria.getPhone().isBlank()) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("phone")), "%" + criteria.getPhone().toLowerCase() + "%"));
            }

            return predicates;
        };
    }
}
