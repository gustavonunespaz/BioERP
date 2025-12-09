package com.bioerp.biodesk.core.application;

import com.bioerp.biodesk.core.domain.model.Alert;
import com.bioerp.biodesk.core.ports.AlertRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryAlertRepository implements AlertRepository {

    private final Map<UUID, Alert> storage = new ConcurrentHashMap<>();

    @Override
    public Alert save(Alert alert) {
        storage.put(alert.getId(), alert);
        return alert;
    }

    @Override
    public boolean existsByDedupKey(String dedupKey) {
        return storage.values().stream()
                .anyMatch(alert -> alert.getDedupKey().equals(dedupKey));
    }

    @Override
    public List<Alert> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<Alert> findById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }
}
