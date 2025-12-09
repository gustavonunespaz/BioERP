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

    @Column(nullable = false)
    private String name;

    @Column(name = "issuing_authority", nullable = false)
    private String issuingAuthority;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    @Column(name = "renewal_lead_time_days", nullable = false)
    private int renewalLeadTimeDays;

    @Column(name = "renewal_requested", nullable = false)
    private boolean renewalRequested;

    @Column(name = "renewal_requested_at")
    private LocalDate renewalRequestedAt;

    @Convert(converter = LicenseConditionsConverter.class)
    @Column(columnDefinition = "TEXT")
    private Set<LicenseCondition> conditions = new HashSet<>();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public EnvironmentalLicense toDomain() {
        return EnvironmentalLicense.builder()
                .id(id)
                .clientId(client.getId())
                .unitId(unit.getId())
                .name(name)
                .issuingAuthority(issuingAuthority)
                .issueDate(issueDate)
                .expirationDate(expirationDate)
                .renewalLeadTimeDays(renewalLeadTimeDays)
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
        entity.setName(license.getName());
        entity.setIssuingAuthority(license.getIssuingAuthority());
        entity.setIssueDate(license.getIssueDate());
        entity.setExpirationDate(license.getExpirationDate());
        entity.setRenewalLeadTimeDays(license.getRenewalLeadTimeDays());
        entity.setRenewalRequested(license.isRenewalRequested());
        entity.setRenewalRequestedAt(license.getRenewalRequestedAt());
        entity.setConditions(new HashSet<>(license.getConditions()));
        return entity;
    }
}
