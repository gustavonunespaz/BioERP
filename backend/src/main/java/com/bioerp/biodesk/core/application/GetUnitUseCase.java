package com.bioerp.biodesk.core.application;

import com.bioerp.biodesk.core.domain.model.Unit;
import com.bioerp.biodesk.core.ports.UnitRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class GetUnitUseCase {

    private final UnitRepository unitRepository;

    public GetUnitUseCase(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    public Optional<Unit> handle(UUID id) {
        return unitRepository.findById(id);
    }
}
