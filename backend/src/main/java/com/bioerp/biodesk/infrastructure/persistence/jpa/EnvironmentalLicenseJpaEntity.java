package com.bioerp.biodesk.infrastructure.persistence.jpa;

import com.bioerp.biodesk.core.domain.model.EnvironmentalLicense;
import com.bioerp.biodesk.core.domain.model.LicenseCondition;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "environmental_licenses")
public class EnvironmentalLicenseJpaEntity {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private ClientJpaEntity client;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "unit_id", nullable = false)
    private UnitJpaEntity unit;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "agency", nullable = false)
    private String agency;

    @Column(name = "process_number", nullable = false)
    private String processNumber;

    @Column(name = "license_number", nullable = false)
    private String licenseNumber;

    @Column(name = "issuing_authority")
    private String issuingAuthority;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    @Column(nullable = false)
    private String status;

    @Column(name = "renewal_lead_time_days")
    private int renewalLeadTimeDays;

    @Column(name = "renewal_requested")
    private boolean renewalRequested;

    @Column(name = "renewal_requested_at")
    private LocalDate renewalRequestedAt;

    @Convert(converter = LicenseConditionsConverter.class)
    @Column(columnDefinition = "TEXT")
    private Set<LicenseCondition> conditions = new HashSet<>();

    @Column(name = "tags", columnDefinition = "TEXT[]")
    private String[] tags;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at", nullable = false)
    private java.time.OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private java.time.OffsetDateTime updatedAt;

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

    public UnitJpaEntity getUnit() {
        return unit;
    }

    public void setUnit(UnitJpaEntity unit) {
        this.unit = unit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getProcessNumber() {
        return processNumber;
    }

    public void setProcessNumber(String processNumber) {
        this.processNumber = processNumber;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getIssuingAuthority() {
        return issuingAuthority;
    }

    public void setIssuingAuthority(String issuingAuthority) {
        this.issuingAuthority = issuingAuthority;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRenewalLeadTimeDays() {
        return renewalLeadTimeDays;
    }

    public void setRenewalLeadTimeDays(int renewalLeadTimeDays) {
        this.renewalLeadTimeDays = renewalLeadTimeDays;
    }

    public boolean isRenewalRequested() {
        return renewalRequested;
    }

    public void setRenewalRequested(boolean renewalRequested) {
        this.renewalRequested = renewalRequested;
    }

    public LocalDate getRenewalRequestedAt() {
        return renewalRequestedAt;
    }

    public void setRenewalRequestedAt(LocalDate renewalRequestedAt) {
        this.renewalRequestedAt = renewalRequestedAt;
    }

    public Set<LicenseCondition> getConditions() {
        return conditions;
    }

    public void setConditions(Set<LicenseCondition> conditions) {
        this.conditions = conditions;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public java.time.OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.time.OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public java.time.OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(java.time.OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public EnvironmentalLicense toDomain() {
        return EnvironmentalLicense.builder()
                .id(id)
                .clientId(client.getId())
                .unitId(unit.getId())
                .name(type)
                .issuingAuthority(agency != null ? agency : issuingAuthority)
                .issueDate(issueDate)
                .expirationDate(expirationDate)
                .renewalLeadTimeDays(renewalLeadTimeDays > 0 ? renewalLeadTimeDays : 1)
                .renewalRequested(renewalRequested)
                .renewalRequestedAt(renewalRequestedAt)
                .conditions(conditions)
                .build();
    }

    public static EnvironmentalLicenseJpaEntity fromDomain(EnvironmentalLicense license, UnitJpaEntity unitEntity) {
        EnvironmentalLicenseJpaEntity entity = new EnvironmentalLicenseJpaEntity();
        entity.setId(license.getId());
        entity.setClient(unitEntity.getClient());
        entity.setUnit(unitEntity);
        entity.setType(license.getName());
        entity.setAgency(license.getIssuingAuthority());
        entity.setProcessNumber("PENDING");
        entity.setLicenseNumber("PENDING");
        entity.setIssuingAuthority(license.getIssuingAuthority());
        entity.setIssueDate(license.getIssueDate());
        entity.setExpirationDate(license.getExpirationDate());
        entity.setStatus("active");
        entity.setRenewalLeadTimeDays(license.getRenewalLeadTimeDays());
        entity.setRenewalRequested(license.isRenewalRequested());
        entity.setRenewalRequestedAt(license.getRenewalRequestedAt());
        entity.setConditions(new HashSet<>(license.getConditions()));
        entity.setCreatedAt(java.time.OffsetDateTime.now());
        entity.setUpdatedAt(entity.getCreatedAt());
        return entity;
    }
}
