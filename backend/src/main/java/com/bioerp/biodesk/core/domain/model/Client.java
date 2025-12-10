package com.bioerp.biodesk.core.domain.model;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

public class Client {

    private final UUID id;
    private final String name;
    private final String tradeName;
    private final String cnpj;
    private final String segment;
    private final String status;
    private final String mainContactName;
    private final String mainContactEmail;
    private final String mainContactPhone;
    private final String notes;
    private final OffsetDateTime createdAt;
    private final OffsetDateTime updatedAt;

    private Client(Builder builder) {
        this.id = Objects.requireNonNull(builder.id, "id is required");
        this.name = requireNonBlank(builder.name, "name is required");
        this.cnpj = requireNonBlank(builder.cnpj, "cnpj is required");
        this.tradeName = builder.tradeName == null || builder.tradeName.isBlank() ? this.name : builder.tradeName;
        this.segment = builder.segment;
        this.status = builder.status == null || builder.status.isBlank() ? "active" : builder.status;
        this.mainContactName = builder.mainContactName;
        this.mainContactEmail = builder.mainContactEmail;
        this.mainContactPhone = builder.mainContactPhone;
        this.notes = builder.notes;
        this.createdAt = Objects.requireNonNullElse(builder.createdAt, OffsetDateTime.now());
        this.updatedAt = Objects.requireNonNullElse(builder.updatedAt, this.createdAt);
    }

    public static Builder builder() {
        return new Builder();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTradeName() {
        return tradeName;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getSegment() {
        return segment;
    }

    public String getStatus() {
        return status;
    }

    public String getMainContactName() {
        return mainContactName;
    }

    public String getMainContactEmail() {
        return mainContactEmail;
    }

    public String getMainContactPhone() {
        return mainContactPhone;
    }

    public String getNotes() {
        return notes;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    private static String requireNonBlank(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    public static final class Builder {
        private UUID id;
        private String name;
        private String cnpj;
        private String tradeName;
        private String segment;
        private String status;
        private String mainContactName;
        private String mainContactEmail;
        private String mainContactPhone;
        private String notes;
        private OffsetDateTime createdAt;
        private OffsetDateTime updatedAt;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder cnpj(String cnpj) {
            this.cnpj = cnpj;
            return this;
        }

        public Builder tradeName(String tradeName) {
            this.tradeName = tradeName;
            return this;
        }

        public Builder segment(String segment) {
            this.segment = segment;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder mainContactName(String mainContactName) {
            this.mainContactName = mainContactName;
            return this;
        }

        public Builder mainContactEmail(String mainContactEmail) {
            this.mainContactEmail = mainContactEmail;
            return this;
        }

        public Builder mainContactPhone(String mainContactPhone) {
            this.mainContactPhone = mainContactPhone;
            return this;
        }

        public Builder notes(String notes) {
            this.notes = notes;
            return this;
        }

        public Builder createdAt(OffsetDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(OffsetDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Client build() {
            if (id == null) {
                id = UUID.randomUUID();
            }
            return new Client(this);
        }
    }
}
