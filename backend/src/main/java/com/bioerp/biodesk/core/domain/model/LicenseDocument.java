package com.bioerp.biodesk.core.domain.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class LicenseDocument {

    private final UUID id;
    private final UUID licenseId;
    private final String type;
    private final List<LicenseDocumentVersion> versions;

    private LicenseDocument(Builder builder) {
        this.id = Objects.requireNonNull(builder.id, "id is required");
        this.licenseId = Objects.requireNonNull(builder.licenseId, "licenseId is required");
        this.type = requireNonBlank(builder.type, "type is required");
        this.versions = Collections.unmodifiableList(new ArrayList<>(builder.versions));
    }

    public static Builder builder() {
        return new Builder();
    }

    public static LicenseDocument create(UUID licenseId, String type) {
        return LicenseDocument.builder()
                .id(UUID.randomUUID())
                .licenseId(licenseId)
                .type(type)
                .versions(Collections.emptyList())
                .build();
    }

    public LicenseDocument addNewVersion(String fileName, String contentType, byte[] fileData) {
        return addNewVersion(fileName, contentType, fileData, Instant.now());
    }

    public LicenseDocument addNewVersion(String fileName, String contentType, byte[] fileData, Instant uploadedAt) {
        List<LicenseDocumentVersion> updatedVersions = new ArrayList<>();
        for (LicenseDocumentVersion version : versions) {
            if (version.isActive()) {
                updatedVersions.add(version.deactivate());
            } else {
                updatedVersions.add(version);
            }
        }
        int nextVersionNumber = versions.stream()
                .mapToInt(LicenseDocumentVersion::getVersionNumber)
                .max()
                .orElse(0) + 1;
        LicenseDocumentVersion newVersion = LicenseDocumentVersion.builder()
                .id(UUID.randomUUID())
                .documentId(id)
                .versionNumber(nextVersionNumber)
                .fileName(fileName)
                .contentType(contentType)
                .fileData(fileData)
                .uploadedAt(uploadedAt)
                .active(true)
                .build();
        updatedVersions.add(newVersion);
        return LicenseDocument.builder()
                .id(id)
                .licenseId(licenseId)
                .type(type)
                .versions(updatedVersions)
                .build();
    }

    public Optional<LicenseDocumentVersion> getActiveVersion() {
        return versions.stream().filter(LicenseDocumentVersion::isActive).findFirst();
    }

    public UUID getId() {
        return id;
    }

    public UUID getLicenseId() {
        return licenseId;
    }

    public String getType() {
        return type;
    }

    public List<LicenseDocumentVersion> getVersions() {
        return versions;
    }

    private static String requireNonBlank(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    public static final class Builder {
        private UUID id;
        private UUID licenseId;
        private String type;
        private List<LicenseDocumentVersion> versions = new ArrayList<>();

        private Builder() {
        }

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder licenseId(UUID licenseId) {
            this.licenseId = licenseId;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder versions(List<LicenseDocumentVersion> versions) {
            this.versions = new ArrayList<>(versions);
            return this;
        }

        public LicenseDocument build() {
            if (id == null) {
                id = UUID.randomUUID();
            }
            return new LicenseDocument(this);
        }
    }
}
