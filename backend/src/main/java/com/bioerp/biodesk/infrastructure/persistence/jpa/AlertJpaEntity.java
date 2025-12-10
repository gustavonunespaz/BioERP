package com.bioerp.biodesk.infrastructure.persistence.jpa;

import com.bioerp.biodesk.core.domain.model.Alert;
import com.bioerp.biodesk.core.domain.model.AlertType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "alerts")
public class AlertJpaEntity {

    @Id
    private UUID id;

    @Column(name = "client_id", nullable = false)
    private UUID clientId;

    @Column(name = "unit_id", nullable = false)
    private UUID unitId;

    @Column(name = "license_id", nullable = false)
    private UUID licenseId;

    @Enumerated(EnumType.STRING)
    @Column(name = "alert_type", nullable = false)
    private AlertType type;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(name = "dedup_key", nullable = false, unique = true)
    private String dedupKey;

    @Column(name = "is_read", nullable = false)
    private boolean read;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public UUID getUnitId() {
        return unitId;
    }

    public void setUnitId(UUID unitId) {
        this.unitId = unitId;
    }

    public UUID getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(UUID licenseId) {
        this.licenseId = licenseId;
    }

    public AlertType getType() {
        return type;
    }

    public void setType(AlertType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDedupKey() {
        return dedupKey;
    }

    public void setDedupKey(String dedupKey) {
        this.dedupKey = dedupKey;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Alert toDomain() {
        return Alert.builder()
                .id(id)
                .clientId(clientId)
                .unitId(unitId)
                .licenseId(licenseId)
                .type(type)
                .title(title)
                .message(message)
                .dedupKey(dedupKey)
                .read(read)
                .createdAt(createdAt)
                .build();
    }

    public static AlertJpaEntity fromDomain(Alert alert) {
        AlertJpaEntity entity = new AlertJpaEntity();
        entity.setId(alert.getId());
        entity.setClientId(alert.getClientId());
        entity.setUnitId(alert.getUnitId());
        entity.setLicenseId(alert.getLicenseId());
        entity.setType(alert.getType());
        entity.setTitle(alert.getTitle());
        entity.setMessage(alert.getMessage());
        entity.setDedupKey(alert.getDedupKey());
        entity.setRead(alert.isRead());
        entity.setCreatedAt(alert.getCreatedAt());
        return entity;
    }
}
