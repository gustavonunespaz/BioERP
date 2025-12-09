package com.bioerp.biodesk.core.ports;

import com.bioerp.biodesk.core.domain.model.Alert;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AlertRepository {
    Alert save(Alert alert);
    boolean existsByDedupKey(String dedupKey);
    List<Alert> findAll();
    Optional<Alert> findById(UUID id);
}
