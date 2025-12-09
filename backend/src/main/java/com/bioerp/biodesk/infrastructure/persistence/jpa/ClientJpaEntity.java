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

    public Client toDomain() {
        return Client.builder()
                .id(id)
                .name(name)
                .cnpj(cnpj)
                .createdAt(createdAt)
                .build();
    }

    public static ClientJpaEntity fromDomain(Client client) {
        ClientJpaEntity entity = new ClientJpaEntity();
        entity.setId(client.getId());
        entity.setName(client.getName());
        entity.setCnpj(client.getCnpj());
        entity.setCreatedAt(client.getCreatedAt());
        return entity;
    }
}
