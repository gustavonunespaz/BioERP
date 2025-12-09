package com.bioerp.biodesk.infrastructure.persistence.jpa;

import com.bioerp.biodesk.core.domain.model.LicenseDocumentVersion;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "license_document_versions")
public class LicenseDocumentVersionJpaEntity {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "document_id", nullable = false)
    private LicenseDocumentJpaEntity document;

    @Column(name = "version_number", nullable = false)
    private int versionNumber;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Lob
    @Column(name = "file_data", nullable = false)
    private byte[] fileData;

    @Column(name = "uploaded_at", nullable = false)
    private Instant uploadedAt;

    @Column(nullable = false)
    private boolean active;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LicenseDocumentJpaEntity getDocument() {
        return document;
    }

    public void setDocument(LicenseDocumentJpaEntity document) {
        this.document = document;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public Instant getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Instant uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LicenseDocumentVersion toDomain() {
        return LicenseDocumentVersion.builder()
                .id(id)
                .documentId(document.getId())
                .versionNumber(versionNumber)
                .fileName(fileName)
                .contentType(contentType)
                .fileData(fileData)
                .uploadedAt(uploadedAt)
                .active(active)
                .build();
    }

    public static LicenseDocumentVersionJpaEntity fromDomain(LicenseDocumentVersion version, LicenseDocumentJpaEntity documentEntity) {
        LicenseDocumentVersionJpaEntity entity = new LicenseDocumentVersionJpaEntity();
        entity.setId(version.getId());
        entity.setDocument(documentEntity);
        entity.setVersionNumber(version.getVersionNumber());
        entity.setFileName(version.getFileName());
        entity.setContentType(version.getContentType());
        entity.setFileData(version.getFileData());
        entity.setUploadedAt(version.getUploadedAt());
        entity.setActive(version.isActive());
        return entity;
    }
}
