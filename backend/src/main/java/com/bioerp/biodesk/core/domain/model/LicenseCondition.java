package com.bioerp.biodesk.core.domain.model;

import java.time.Period;
import java.util.Objects;

public class LicenseCondition {

    private final String name;
    private final String documentType;
    private final Period periodicity;

    private LicenseCondition(Builder builder) {
        this.name = requireNonBlank(builder.name, "Condition name is required");
        this.documentType = requireNonBlank(builder.documentType, "Document type is required");
        this.periodicity = Objects.requireNonNull(builder.periodicity, "Periodicity is required");
    }

    public static Builder builder() {
        return new Builder();
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

    private static String requireNonBlank(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    public static final class Builder {
        private String name;
        private String documentType;
        private Period periodicity;

        private Builder() {
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

        public LicenseCondition build() {
            return new LicenseCondition(this);
        }
    }
}
