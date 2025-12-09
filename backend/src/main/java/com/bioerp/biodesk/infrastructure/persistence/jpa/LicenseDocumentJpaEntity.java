package com.bioerp.biodesk.infrastructure.persistence.jpa;

import com.bioerp.biodesk.core.domain.model.LicenseDocument;
import com.bioerp.biodesk.core.domain.model.LicenseDocumentVersion;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "license_documents")
public class LicenseDocumentJpaEntity {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "license_id", nullable = false)
    private EnvironmentalLicenseJpaEntity license;

    @Column(nullable = false)
    private String type;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("versionNumber ASC")
    private List<LicenseDocumentVersionJpaEntity> versions = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public EnvironmentalLicenseJpaEntity getLicense() {
        return license;
    }

    public void setLicense(EnvironmentalLicenseJpaEntity license) {
        this.license = license;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<LicenseDocumentVersionJpaEntity> getVersions() {
        return versions;
    }

    public void setVersions(List<LicenseDocumentVersionJpaEntity> versions) {
        this.versions.clear();
        this.versions.addAll(versions);
    }

    public LicenseDocument toDomain() {
        List<LicenseDocumentVersion> domainVersions = versions.stream()
                .map(LicenseDocumentVersionJpaEntity::toDomain)
                .toList();
        return LicenseDocument.builder()
                .id(id)
                .licenseId(license.getId())
                .type(type)
                .versions(domainVersions)
                .build();
    }

    public static LicenseDocumentJpaEntity fromDomain(LicenseDocument document, EnvironmentalLicenseJpaEntity licenseEntity) {
        LicenseDocumentJpaEntity entity = new LicenseDocumentJpaEntity();
        entity.setId(document.getId());
        entity.setLicense(licenseEntity);
        entity.setType(document.getType());
        List<LicenseDocumentVersionJpaEntity> versionEntities = document.getVersions().stream()
                .map(version -> LicenseDocumentVersionJpaEntity.fromDomain(version, entity))
                .toList();
        entity.setVersions(versionEntities);
        return entity;
    }
}
