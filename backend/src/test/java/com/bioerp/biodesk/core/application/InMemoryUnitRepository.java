package com.bioerp.biodesk.core.application;

import com.bioerp.biodesk.core.domain.model.Unit;
import com.bioerp.biodesk.core.ports.UnitRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

class InMemoryUnitRepository implements UnitRepository {

    private final Map<UUID, Unit> storage = new HashMap<>();

    @Override
    public Unit save(Unit unit) {
        storage.put(unit.getId(), unit);
        return unit;
    }

    @Override
    public boolean existsByCnpj(String cnpj) {
        return storage.values().stream().anyMatch(unit -> unit.getCnpj().equals(cnpj));
    }

    @Override
    public Optional<Unit> findById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Unit> findByClientId(UUID clientId) {
        return storage.values().stream()
                .filter(unit -> unit.getClientId().equals(clientId))
                .toList();
    }
}
