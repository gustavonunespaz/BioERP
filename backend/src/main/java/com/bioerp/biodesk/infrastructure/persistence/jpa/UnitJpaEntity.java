package com.bioerp.biodesk.infrastructure.persistence.jpa;

import com.bioerp.biodesk.core.domain.model.Unit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "units")
public class UnitJpaEntity {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private ClientJpaEntity client;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true, length = 14)
    private String cnpj;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ClientJpaEntity getClient() {
        return client;
    }

    public void setClient(ClientJpaEntity client) {
        this.client = client;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Unit toDomain() {
        return Unit.builder()
                .id(id)
                .clientId(client.getId())
                .name(name)
                .cnpj(cnpj)
                .createdAt(createdAt)
                .build();
    }

    public static UnitJpaEntity fromDomain(Unit unit, ClientJpaEntity clientEntity) {
        UnitJpaEntity entity = new UnitJpaEntity();
        entity.setId(unit.getId());
        entity.setClient(clientEntity);
        entity.setName(unit.getName());
        entity.setCnpj(unit.getCnpj());
        entity.setCreatedAt(unit.getCreatedAt());
        return entity;
    }
}
