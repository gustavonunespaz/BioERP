package com.bioerp.biodesk.core.domain.model;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

public class Unit {

    private final UUID id;
    private final UUID clientId;
    private final String name;
    private final String cnpj;
    private final String addressLine;
    private final String city;
    private final String state;
    private final String activity;
    private final String notes;
    private final OffsetDateTime createdAt;
    private final OffsetDateTime updatedAt;

    private Unit(Builder builder) {
        this.id = Objects.requireNonNull(builder.id, "id is required");
        this.clientId = Objects.requireNonNull(builder.clientId, "clientId is required");
        this.name = requireNonBlank(builder.name, "name is required");
        this.cnpj = requireNonBlank(builder.cnpj, "cnpj is required");
        this.addressLine = builder.addressLine;
        this.city = builder.city;
        this.state = builder.state;
        this.activity = builder.activity;
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

    public UUID getClientId() {
        return clientId;
    }

    public String getName() {
        return name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getActivity() {
        return activity;
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
        private UUID clientId;
        private String name;
        private String cnpj;
        private String addressLine;
        private String city;
        private String state;
        private String activity;
        private String notes;
        private OffsetDateTime createdAt;
        private OffsetDateTime updatedAt;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder clientId(UUID clientId) {
            this.clientId = clientId;
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

        public Builder addressLine(String addressLine) {
            this.addressLine = addressLine;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder state(String state) {
            this.state = state;
            return this;
        }

        public Builder activity(String activity) {
            this.activity = activity;
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

        public Unit build() {
            if (id == null) {
                id = UUID.randomUUID();
            }
            return new Unit(this);
        }
    }
}
