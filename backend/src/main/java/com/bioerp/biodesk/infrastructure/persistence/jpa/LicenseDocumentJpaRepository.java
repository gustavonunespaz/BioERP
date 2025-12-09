package com.bioerp.biodesk.infrastructure.persistence.jpa;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LicenseDocumentJpaRepository extends JpaRepository<LicenseDocumentJpaEntity, UUID> {
    Optional<LicenseDocumentJpaEntity> findByLicense_IdAndType(UUID licenseId, String type);
    List<LicenseDocumentJpaEntity> findAllByLicense_Id(UUID licenseId);
}
