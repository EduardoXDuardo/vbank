package com.eduardoxduardo.vbank.repository.specification;

import org.springframework.data.jpa.domain.Specification;

// Generic Specification Builder Interface
// E - Entity Type
// C - Criteria Type
public interface SpecificationBuilder<E, C> {
    Specification<E> build(C criteria);
}
