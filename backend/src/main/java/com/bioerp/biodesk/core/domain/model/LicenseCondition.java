package com.bioerp.biodesk.core.domain.model;

import java.time.Period;
import java.util.Objects;
import java.util.UUID;

public class LicenseCondition {

    private final UUID id;
    private final String name;
    private final String documentType;
    private final Period periodicity;
    private final LicenseConditionStatus status;

    private LicenseCondition(Builder builder) {
        this.id = builder.id == null ? UUID.randomUUID() : builder.id;
        this.name = requireNonBlank(builder.name, "Condition name is required");
        this.documentType = requireNonBlank(builder.documentType, "Document type is required");
        this.periodicity = Objects.requireNonNull(builder.periodicity, "Periodicity is required");
        this.status = Objects.requireNonNullElse(builder.status, LicenseConditionStatus.PENDING);
    }

    public static Builder builder() {
        return new Builder();
    }

    public LicenseCondition withStatus(LicenseConditionStatus status) {
        return builder()
                .id(this.id)
                .name(this.name)
                .documentType(this.documentType)
                .periodicity(this.periodicity)
                .status(status)
                .build();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDocumentType() {
        return documentType;
    }

    public Period getPeriodicity() {
        return periodicity;
    }

    public LicenseConditionStatus getStatus() {
        return status;
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
        private String documentType;
        private Period periodicity;
        private LicenseConditionStatus status = LicenseConditionStatus.PENDING;

        private Builder() {
        }

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder documentType(String documentType) {
            this.documentType = documentType;
            return this;
        }

        public Builder periodicity(Period periodicity) {
            this.periodicity = periodicity;
            return this;
        }

        public Builder status(LicenseConditionStatus status) {
            this.status = status;
            return this;
        }

        public LicenseCondition build() {
            return new LicenseCondition(this);
        }
    }
}
