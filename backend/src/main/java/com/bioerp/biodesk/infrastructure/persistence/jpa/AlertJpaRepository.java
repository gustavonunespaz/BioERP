package com.bioerp.biodesk.infrastructure.persistence.jpa;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertJpaRepository extends JpaRepository<AlertJpaEntity, UUID> {
    boolean existsByDedupKey(String dedupKey);
}
