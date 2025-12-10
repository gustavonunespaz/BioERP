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

    @Column(unique = true, length = 20)
    private String cnpj;

    @Column(name = "address_line")
    private String addressLine;

    private String city;

    private String state;

    private String activity;

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

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
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

    public Unit toDomain() {
        return Unit.builder()
                .id(id)
                .clientId(client.getId())
                .name(name)
                .cnpj(cnpj)
                .addressLine(addressLine)
                .city(city)
                .state(state)
                .activity(activity)
                .notes(notes)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public static UnitJpaEntity fromDomain(Unit unit, ClientJpaEntity clientEntity) {
        UnitJpaEntity entity = new UnitJpaEntity();
        entity.setId(unit.getId());
        entity.setClient(clientEntity);
        entity.setName(unit.getName());
        entity.setCnpj(unit.getCnpj());
        entity.setAddressLine(unit.getAddressLine());
        entity.setCity(unit.getCity());
        entity.setState(unit.getState());
        entity.setActivity(unit.getActivity());
        entity.setNotes(unit.getNotes());
        entity.setCreatedAt(unit.getCreatedAt());
        entity.setUpdatedAt(unit.getUpdatedAt());
        return entity;
    }
}
