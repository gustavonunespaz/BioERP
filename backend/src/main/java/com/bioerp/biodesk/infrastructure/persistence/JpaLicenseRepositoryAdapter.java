package com.bioerp.biodesk.infrastructure.persistence;

import com.bioerp.biodesk.core.application.ResourceNotFoundException;
import com.bioerp.biodesk.core.domain.model.EnvironmentalLicense;
import com.bioerp.biodesk.core.ports.LicenseRepository;
import com.bioerp.biodesk.core.ports.query.LicenseSearchQuery;
import com.bioerp.biodesk.infrastructure.persistence.jpa.EnvironmentalLicenseJpaEntity;
import com.bioerp.biodesk.infrastructure.persistence.jpa.EnvironmentalLicenseJpaRepository;
import com.bioerp.biodesk.infrastructure.persistence.jpa.UnitJpaEntity;
import com.bioerp.biodesk.infrastructure.persistence.jpa.UnitJpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class JpaLicenseRepositoryAdapter implements LicenseRepository {

    private final EnvironmentalLicenseJpaRepository licenseJpaRepository;
    private final UnitJpaRepository unitJpaRepository;

    public JpaLicenseRepositoryAdapter(EnvironmentalLicenseJpaRepository licenseJpaRepository,
                                       UnitJpaRepository unitJpaRepository) {
        this.licenseJpaRepository = licenseJpaRepository;
        this.unitJpaRepository = unitJpaRepository;
    }

    @Override
    public EnvironmentalLicense save(EnvironmentalLicense license) {
        UnitJpaEntity unitEntity = unitJpaRepository.findById(license.getUnitId())
                .orElseThrow(() -> new ResourceNotFoundException("Unit not found"));
        EnvironmentalLicenseJpaEntity entity = EnvironmentalLicenseJpaEntity.fromDomain(license, unitEntity);
        return licenseJpaRepository.save(entity).toDomain();
    }

    @Override
    public Optional<EnvironmentalLicense> findById(UUID id) {
        return licenseJpaRepository.findById(id).map(EnvironmentalLicenseJpaEntity::toDomain);
    }

    @Override
    public List<EnvironmentalLicense> findAll(LicenseSearchQuery query) {
        List<EnvironmentalLicenseJpaEntity> entities;
        if (query.clientId() != null && query.unitId() != null) {
            entities = licenseJpaRepository.findAllByClient_IdAndUnit_Id(query.clientId(), query.unitId());
        } else if (query.clientId() != null) {
            entities = licenseJpaRepository.findAllByClient_Id(query.clientId());
        } else if (query.unitId() != null) {
            entities = licenseJpaRepository.findAllByUnit_Id(query.unitId());
        } else {
            entities = licenseJpaRepository.findAll();
        }
        return entities.stream().map(EnvironmentalLicenseJpaEntity::toDomain).toList();
    }
}
