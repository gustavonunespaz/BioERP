package com.bioerp.biodesk.infrastructure.persistence.jpa;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnvironmentalLicenseJpaRepository extends JpaRepository<EnvironmentalLicenseJpaEntity, UUID> {
    List<EnvironmentalLicenseJpaEntity> findAllByClient_Id(UUID clientId);
    List<EnvironmentalLicenseJpaEntity> findAllByUnit_Id(UUID unitId);
    List<EnvironmentalLicenseJpaEntity> findAllByClient_IdAndUnit_Id(UUID clientId, UUID unitId);
}
