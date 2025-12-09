package com.bioerp.biodesk.core.domain.model;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class EnvironmentalLicense {

    private final UUID id;
    private final UUID clientId;
    private final UUID unitId;
    private final String name;
    private final String issuingAuthority;
    private final LocalDate issueDate;
    private final LocalDate expirationDate;
    private final int renewalLeadTimeDays;
    private final boolean renewalRequested;
    private final LocalDate renewalRequestedAt;
    private final Set<LicenseCondition> conditions;

    private EnvironmentalLicense(Builder builder) {
        this.id = Objects.requireNonNull(builder.id, "id is required");
        this.clientId = Objects.requireNonNull(builder.clientId, "clientId is required");
        this.unitId = Objects.requireNonNull(builder.unitId, "unitId is required");
        this.name = requireNonBlank(builder.name, "name is required");
        this.issuingAuthority = requireNonBlank(builder.issuingAuthority, "issuingAuthority is required");
        this.issueDate = Objects.requireNonNull(builder.issueDate, "issueDate is required");
        this.expirationDate = Objects.requireNonNull(builder.expirationDate, "expirationDate is required");
        if (!builder.expirationDate.isAfter(builder.issueDate)) {
            throw new IllegalArgumentException("expirationDate must be after issueDate");
        }
        if (builder.renewalLeadTimeDays <= 0) {
            throw new IllegalArgumentException("renewalLeadTimeDays must be greater than zero");
        }
        this.renewalLeadTimeDays = builder.renewalLeadTimeDays;
        if (builder.renewalRequestedAt != null && builder.renewalRequestedAt.isBefore(builder.issueDate)) {
            throw new IllegalArgumentException("renewalRequestedAt cannot be before issueDate");
        }
        this.renewalRequestedAt = builder.renewalRequestedAt;
        this.renewalRequested = builder.renewalRequested;
        Set<LicenseCondition> copy = new HashSet<>(builder.conditions);
        this.conditions = Collections.unmodifiableSet(copy);
    }

    public static Builder builder() {
        return new Builder();
    }

    public EnvironmentalLicense requestRenewal(LocalDate requestedAt) {
        return EnvironmentalLicense.builder()
                .id(this.id)
                .clientId(this.clientId)
                .unitId(this.unitId)
                .name(this.name)
                .issuingAuthority(this.issuingAuthority)
                .issueDate(this.issueDate)
                .expirationDate(this.expirationDate)
                .renewalLeadTimeDays(this.renewalLeadTimeDays)
                .renewalRequested(true)
                .renewalRequestedAt(requestedAt)
                .conditions(this.conditions)
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

    public String getName() {
        return name;
    }

    public String getIssuingAuthority() {
        return issuingAuthority;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public int getRenewalLeadTimeDays() {
        return renewalLeadTimeDays;
    }

    public boolean isRenewalRequested() {
        return renewalRequested;
    }

    public LocalDate getRenewalRequestedAt() {
        return renewalRequestedAt;
    }

    public Set<LicenseCondition> getConditions() {
        return conditions;
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
        private String name;
        private String issuingAuthority;
        private LocalDate issueDate;
        private LocalDate expirationDate;
        private int renewalLeadTimeDays;
        private boolean renewalRequested;
        private LocalDate renewalRequestedAt;
        private Set<LicenseCondition> conditions = new HashSet<>();

        private Builder() {
        }

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder unitId(UUID unitId) {
            this.unitId = unitId;
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

        public Builder issuingAuthority(String issuingAuthority) {
            this.issuingAuthority = issuingAuthority;
            return this;
        }

        public Builder issueDate(LocalDate issueDate) {
            this.issueDate = issueDate;
            return this;
        }

        public Builder expirationDate(LocalDate expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        public Builder renewalLeadTimeDays(int renewalLeadTimeDays) {
            this.renewalLeadTimeDays = renewalLeadTimeDays;
            return this;
        }

        public Builder renewalRequested(boolean renewalRequested) {
            this.renewalRequested = renewalRequested;
            return this;
        }

        public Builder renewalRequestedAt(LocalDate renewalRequestedAt) {
            this.renewalRequestedAt = renewalRequestedAt;
            return this;
        }

        public Builder conditions(Set<LicenseCondition> conditions) {
            this.conditions = new HashSet<>(conditions);
            return this;
        }

        public Builder addCondition(LicenseCondition condition) {
            this.conditions.add(condition);
            return this;
        }

        public EnvironmentalLicense build() {
            if (id == null) {
                id = UUID.randomUUID();
            }
            return new EnvironmentalLicense(this);
        }
    }
}
