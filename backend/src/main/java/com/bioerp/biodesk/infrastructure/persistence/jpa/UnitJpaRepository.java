package com.bioerp.biodesk.infrastructure.persistence.jpa;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitJpaRepository extends JpaRepository<UnitJpaEntity, UUID> {
    boolean existsByCnpj(String cnpj);
    List<UnitJpaEntity> findAllByClient_Id(UUID clientId);
}
