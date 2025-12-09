package com.bioerp.biodesk.core.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Alert {

    private final UUID id;
    private final UUID clientId;
    private final UUID unitId;
    private final UUID licenseId;
    private final AlertType type;
    private final String title;
    private final String message;
    private final String dedupKey;
    private final boolean read;
    private final LocalDateTime createdAt;

    private Alert(Builder builder) {
        this.id = Objects.requireNonNull(builder.id, "id is required");
        this.clientId = Objects.requireNonNull(builder.clientId, "clientId is required");
        this.unitId = Objects.requireNonNull(builder.unitId, "unitId is required");
        this.licenseId = Objects.requireNonNull(builder.licenseId, "licenseId is required");
        this.type = Objects.requireNonNull(builder.type, "type is required");
        this.title = requireNonBlank(builder.title, "title is required");
        this.message = requireNonBlank(builder.message, "message is required");
        this.dedupKey = requireNonBlank(builder.dedupKey, "dedupKey is required");
        this.read = builder.read;
        this.createdAt = Objects.requireNonNull(builder.createdAt, "createdAt is required");
    }

    public static Builder builder() {
        return new Builder();
    }

    public Alert markAsRead() {
        return Alert.builder()
                .id(this.id)
                .clientId(this.clientId)
                .unitId(this.unitId)
                .licenseId(this.licenseId)
                .type(this.type)
                .title(this.title)
                .message(this.message)
                .dedupKey(this.dedupKey)
                .read(true)
                .createdAt(this.createdAt)
                .build();
    }

    public UUID getId() {
        return id;
    }

    public UUID getClientId() {
        return clientId;
    }

    public UUID getUnitId() {
        return unitId;
    }

    public UUID getLicenseId() {
        return licenseId;
    }

    public AlertType getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getDedupKey() {
        return dedupKey;
    }

    public boolean isRead() {
        return read;
    }

    public LocalDateTime getCreatedAt() {
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
        private UUID clientId;
        private UUID unitId;
        private UUID licenseId;
        private AlertType type;
        private String title;
        private String message;
        private String dedupKey;
        private boolean read;
        private LocalDateTime createdAt;

        private Builder() {
        }

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder clientId(UUID clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder unitId(UUID unitId) {
            this.unitId = unitId;
            return this;
        }

        public Builder licenseId(UUID licenseId) {
            this.licenseId = licenseId;
            return this;
        }

        public Builder type(AlertType type) {
            this.type = type;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder dedupKey(String dedupKey) {
            this.dedupKey = dedupKey;
            return this;
        }

        public Builder read(boolean read) {
            this.read = read;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Alert build() {
            if (id == null) {
                id = UUID.randomUUID();
            }
            return new Alert(this);
        }
    }
}
