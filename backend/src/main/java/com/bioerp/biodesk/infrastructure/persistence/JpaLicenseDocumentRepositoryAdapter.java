package com.bioerp.biodesk.infrastructure.persistence;

import com.bioerp.biodesk.core.application.ResourceNotFoundException;
import com.bioerp.biodesk.core.domain.model.LicenseDocument;
import com.bioerp.biodesk.core.ports.LicenseDocumentRepository;
import com.bioerp.biodesk.infrastructure.persistence.jpa.EnvironmentalLicenseJpaEntity;
import com.bioerp.biodesk.infrastructure.persistence.jpa.EnvironmentalLicenseJpaRepository;
import com.bioerp.biodesk.infrastructure.persistence.jpa.LicenseDocumentJpaEntity;
import com.bioerp.biodesk.infrastructure.persistence.jpa.LicenseDocumentJpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class JpaLicenseDocumentRepositoryAdapter implements LicenseDocumentRepository {

    private final LicenseDocumentJpaRepository licenseDocumentJpaRepository;
    private final EnvironmentalLicenseJpaRepository environmentalLicenseJpaRepository;

    public JpaLicenseDocumentRepositoryAdapter(LicenseDocumentJpaRepository licenseDocumentJpaRepository,
                                               EnvironmentalLicenseJpaRepository environmentalLicenseJpaRepository) {
        this.licenseDocumentJpaRepository = licenseDocumentJpaRepository;
        this.environmentalLicenseJpaRepository = environmentalLicenseJpaRepository;
    }

    @Override
    public LicenseDocument save(LicenseDocument document) {
        EnvironmentalLicenseJpaEntity licenseEntity = environmentalLicenseJpaRepository.findById(document.getLicenseId())
                .orElseThrow(() -> new ResourceNotFoundException("License not found"));
        LicenseDocumentJpaEntity entity = LicenseDocumentJpaEntity.fromDomain(document, licenseEntity);
        return licenseDocumentJpaRepository.save(entity).toDomain();
    }

    @Override
    public Optional<LicenseDocument> findById(UUID documentId) {
        return licenseDocumentJpaRepository.findById(documentId).map(LicenseDocumentJpaEntity::toDomain);
    }

    @Override
    public Optional<LicenseDocument> findByLicenseIdAndType(UUID licenseId, String type) {
        return licenseDocumentJpaRepository.findByLicense_IdAndType(licenseId, type)
                .map(LicenseDocumentJpaEntity::toDomain);
    }

    @Override
    public List<LicenseDocument> findByLicenseId(UUID licenseId) {
        return licenseDocumentJpaRepository.findAllByLicense_Id(licenseId).stream()
                .map(LicenseDocumentJpaEntity::toDomain)
                .toList();
    }
}
