package com.bioerp.biodesk.core.domain.model;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

public class Client {

    private final UUID id;
    private final String name;
    private final String cnpj;
    private final OffsetDateTime createdAt;

    private Client(Builder builder) {
        this.id = Objects.requireNonNull(builder.id, "id is required");
        this.name = requireNonBlank(builder.name, "name is required");
        this.cnpj = requireNonBlank(builder.cnpj, "cnpj is required");
        this.createdAt = Objects.requireNonNullElse(builder.createdAt, OffsetDateTime.now());
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

    public String getCnpj() {
        return cnpj;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
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
        private OffsetDateTime createdAt;

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

        public Builder createdAt(OffsetDateTime createdAt) {
            this.createdAt = createdAt;
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
