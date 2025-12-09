package com.bioerp.biodesk.core.domain.model;

import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class LicenseDocumentVersion {

    private final UUID id;
    private final UUID documentId;
    private final int versionNumber;
    private final String fileName;
    private final String contentType;
    private final byte[] fileData;
    private final Instant uploadedAt;
    private final boolean active;

    private LicenseDocumentVersion(Builder builder) {
        this.id = Objects.requireNonNull(builder.id, "id is required");
        this.documentId = Objects.requireNonNull(builder.documentId, "documentId is required");
        if (builder.versionNumber <= 0) {
            throw new IllegalArgumentException("versionNumber must be greater than zero");
        }
        this.versionNumber = builder.versionNumber;
        this.fileName = requireNonBlank(builder.fileName, "fileName is required");
        this.contentType = requireNonBlank(builder.contentType, "contentType is required");
        this.fileData = builder.fileData != null ? Arrays.copyOf(builder.fileData, builder.fileData.length) : new byte[0];
        this.uploadedAt = Objects.requireNonNull(builder.uploadedAt, "uploadedAt is required");
        this.active = builder.active;
    }

    public static Builder builder() {
        return new Builder();
    }

    public LicenseDocumentVersion deactivate() {
        return LicenseDocumentVersion.builder()
                .id(id)
                .documentId(documentId)
                .versionNumber(versionNumber)
                .fileName(fileName)
                .contentType(contentType)
                .fileData(fileData)
                .uploadedAt(uploadedAt)
                .active(false)
                .build();
    }

    public UUID getId() {
        return id;
    }

    public UUID getDocumentId() {
        return documentId;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public String getFileName() {
        return fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public byte[] getFileData() {
        return Arrays.copyOf(fileData, fileData.length);
    }

    public Instant getUploadedAt() {
        return uploadedAt;
    }

    public boolean isActive() {
        return active;
    }

    private static String requireNonBlank(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    public static final class Builder {
        private UUID id;
        private UUID documentId;
        private int versionNumber;
        private String fileName;
        private String contentType;
        private byte[] fileData;
        private Instant uploadedAt;
        private boolean active;

        private Builder() {
        }

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder documentId(UUID documentId) {
            this.documentId = documentId;
            return this;
        }

        public Builder versionNumber(int versionNumber) {
            this.versionNumber = versionNumber;
            return this;
        }

        public Builder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder fileData(byte[] fileData) {
            this.fileData = fileData != null ? Arrays.copyOf(fileData, fileData.length) : null;
            return this;
        }

        public Builder uploadedAt(Instant uploadedAt) {
            this.uploadedAt = uploadedAt;
            return this;
        }

        public Builder active(boolean active) {
            this.active = active;
            return this;
        }

        public LicenseDocumentVersion build() {
            if (id == null) {
                id = UUID.randomUUID();
            }
            if (uploadedAt == null) {
                uploadedAt = Instant.now();
            }
            return new LicenseDocumentVersion(this);
        }
    }
}
