package com.bioerp.biodesk.infrastructure.persistence.jpa;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientJpaRepository extends JpaRepository<ClientJpaEntity, UUID> {
    boolean existsByCnpj(String cnpj);
}
