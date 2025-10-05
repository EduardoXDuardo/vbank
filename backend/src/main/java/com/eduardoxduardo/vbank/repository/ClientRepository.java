package com.eduardoxduardo.vbank.repository;

import com.eduardoxduardo.vbank.model.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {
    boolean existsByDocument(String document);
    boolean existsByEmail(String email);
}
