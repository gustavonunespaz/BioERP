package com.bioerp.biodesk.infrastructure.persistence.jpa;

import com.bioerp.biodesk.core.domain.model.Client;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "clients")
public class ClientJpaEntity {

    @Id
    private UUID id;

    @Column(name = "legal_name", nullable = false)
    private String legalName;

    @Column(name = "trade_name")
    private String tradeName;

    @Column(nullable = false, unique = true, length = 20)
    private String cnpj;

    @Column
    private String segment;

    @Column(nullable = false)
    private String status;

    @Column(name = "main_contact_name")
    private String mainContactName;

    @Column(name = "main_contact_email")
    private String mainContactEmail;

    @Column(name = "main_contact_phone")
    private String mainContactPhone;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMainContactName() {
        return mainContactName;
    }

    public void setMainContactName(String mainContactName) {
        this.mainContactName = mainContactName;
    }

    public String getMainContactEmail() {
        return mainContactEmail;
    }

    public void setMainContactEmail(String mainContactEmail) {
        this.mainContactEmail = mainContactEmail;
    }

    public String getMainContactPhone() {
        return mainContactPhone;
    }

    public void setMainContactPhone(String mainContactPhone) {
        this.mainContactPhone = mainContactPhone;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Client toDomain() {
        return Client.builder()
                .id(id)
                .name(legalName)
                .tradeName(tradeName)
                .cnpj(cnpj)
                .segment(segment)
                .status(status)
                .mainContactName(mainContactName)
                .mainContactEmail(mainContactEmail)
                .mainContactPhone(mainContactPhone)
                .notes(notes)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public static ClientJpaEntity fromDomain(Client client) {
        ClientJpaEntity entity = new ClientJpaEntity();
        entity.setId(client.getId());
        entity.setLegalName(client.getName());
        entity.setTradeName(client.getTradeName());
        entity.setCnpj(client.getCnpj());
        entity.setSegment(client.getSegment());
        entity.setStatus(client.getStatus());
        entity.setMainContactName(client.getMainContactName());
        entity.setMainContactEmail(client.getMainContactEmail());
        entity.setMainContactPhone(client.getMainContactPhone());
        entity.setNotes(client.getNotes());
        entity.setCreatedAt(client.getCreatedAt());
        entity.setUpdatedAt(client.getUpdatedAt());
        return entity;
    }
}
