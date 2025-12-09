package com.bioerp.biodesk.core.ports;

import com.bioerp.biodesk.core.domain.model.Unit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UnitRepository {
    Unit save(Unit unit);
    boolean existsByCnpj(String cnpj);
    Optional<Unit> findById(UUID id);
    List<Unit> findByClientId(UUID clientId);
}
