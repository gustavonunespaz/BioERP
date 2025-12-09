package com.bioerp.biodesk.infrastructure.persistence;

import com.bioerp.biodesk.core.domain.model.Alert;
import com.bioerp.biodesk.core.ports.AlertRepository;
import com.bioerp.biodesk.infrastructure.persistence.jpa.AlertJpaEntity;
import com.bioerp.biodesk.infrastructure.persistence.jpa.AlertJpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class JpaAlertRepositoryAdapter implements AlertRepository {

    private final AlertJpaRepository alertJpaRepository;

    public JpaAlertRepositoryAdapter(AlertJpaRepository alertJpaRepository) {
        this.alertJpaRepository = alertJpaRepository;
    }

    @Override
    public Alert save(Alert alert) {
        AlertJpaEntity entity = AlertJpaEntity.fromDomain(alert);
        return alertJpaRepository.save(entity).toDomain();
    }

    @Override
    public boolean existsByDedupKey(String dedupKey) {
        return alertJpaRepository.existsByDedupKey(dedupKey);
    }

    @Override
    public List<Alert> findAll() {
        return alertJpaRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")).stream()
                .map(AlertJpaEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<Alert> findById(UUID id) {
        return alertJpaRepository.findById(id).map(AlertJpaEntity::toDomain);
    }
}
