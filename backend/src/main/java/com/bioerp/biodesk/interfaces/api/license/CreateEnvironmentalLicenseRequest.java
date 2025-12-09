package com.bioerp.biodesk.interfaces.api.license;

import com.bioerp.biodesk.core.domain.model.LicenseCondition;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.Period;
import java.util.Set;
import java.util.stream.Collectors;

public record CreateEnvironmentalLicenseRequest(
        @NotBlank String name,
        @NotBlank String issuingAuthority,
        @NotNull LocalDate issueDate,
        @NotNull LocalDate expirationDate,
        @Positive int renewalLeadTimeDays,
        @NotNull Boolean renewalRequested,
        LocalDate renewalRequestedAt,
        Set<@Valid LicenseConditionRequest> conditions
) {

    public Set<LicenseCondition> toDomainConditions() {
        if (conditions == null) {
            return Set.of();
        }
        return conditions.stream()
                .map(LicenseConditionRequest::toDomain)
                .collect(Collectors.toSet());
    }

    public record LicenseConditionRequest(
            @NotBlank String name,
            @NotBlank String documentType,
            @Positive int periodicityInMonths
    ) {
        public LicenseCondition toDomain() {
            return LicenseCondition.builder()
                    .name(name)
                    .documentType(documentType)
                    .periodicity(Period.ofMonths(periodicityInMonths))
                    .build();
        }
    }
}
