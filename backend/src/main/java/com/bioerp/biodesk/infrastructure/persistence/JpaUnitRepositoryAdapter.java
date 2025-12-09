package com.bioerp.biodesk.infrastructure.persistence;

import com.bioerp.biodesk.core.application.ResourceNotFoundException;
import com.bioerp.biodesk.core.domain.model.Unit;
import com.bioerp.biodesk.core.ports.UnitRepository;
import com.bioerp.biodesk.infrastructure.persistence.jpa.ClientJpaEntity;
import com.bioerp.biodesk.infrastructure.persistence.jpa.ClientJpaRepository;
import com.bioerp.biodesk.infrastructure.persistence.jpa.UnitJpaEntity;
import com.bioerp.biodesk.infrastructure.persistence.jpa.UnitJpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class JpaUnitRepositoryAdapter implements UnitRepository {

    private final UnitJpaRepository unitJpaRepository;
    private final ClientJpaRepository clientJpaRepository;

    public JpaUnitRepositoryAdapter(UnitJpaRepository unitJpaRepository, ClientJpaRepository clientJpaRepository) {
        this.unitJpaRepository = unitJpaRepository;
        this.clientJpaRepository = clientJpaRepository;
    }

    @Override
    public Unit save(Unit unit) {
        ClientJpaEntity clientEntity = clientJpaRepository.findById(unit.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        UnitJpaEntity entity = UnitJpaEntity.fromDomain(unit, clientEntity);
        return unitJpaRepository.save(entity).toDomain();
    }

    @Override
    public boolean existsByCnpj(String cnpj) {
        return unitJpaRepository.existsByCnpj(cnpj);
    }

    @Override
    public Optional<Unit> findById(UUID id) {
        return unitJpaRepository.findById(id).map(UnitJpaEntity::toDomain);
    }

    @Override
    public List<Unit> findByClientId(UUID clientId) {
        return unitJpaRepository.findAllByClient_Id(clientId).stream().map(UnitJpaEntity::toDomain).toList();
    }
}
