package com.bioerp.biodesk.interfaces.api.license;

import com.bioerp.biodesk.core.domain.model.EnvironmentalLicense;
import com.bioerp.biodesk.core.domain.model.LicenseCondition;
import com.bioerp.biodesk.core.domain.model.LicenseLifecycleStatus;
import com.bioerp.biodesk.core.domain.service.LicenseStatusEvaluator;
import java.time.Period;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record LicenseResponse(
        UUID id,
        UUID clientId,
        UUID unitId,
        String name,
        String issuingAuthority,
        LocalDate issueDate,
        LocalDate expirationDate,
        int renewalLeadTimeDays,
        boolean renewalRequested,
        LocalDate renewalRequestedAt,
        LicenseLifecycleStatus status,
        Set<LicenseConditionResponse> conditions
) {

    public static LicenseResponse from(EnvironmentalLicense license, LicenseStatusEvaluator evaluator) {
        LicenseLifecycleStatus calculatedStatus = evaluator.evaluate(license);
        Set<LicenseConditionResponse> mappedConditions = license.getConditions().stream()
                .map(LicenseConditionResponse::from)
                .collect(Collectors.toSet());
        return new LicenseResponse(
                license.getId(),
                license.getClientId(),
                license.getUnitId(),
                license.getName(),
                license.getIssuingAuthority(),
                license.getIssueDate(),
                license.getExpirationDate(),
                license.getRenewalLeadTimeDays(),
                license.isRenewalRequested(),
                license.getRenewalRequestedAt(),
                calculatedStatus,
                mappedConditions
        );
    }

    public record LicenseConditionResponse(String name, String documentType, int periodicityInMonths) {
        public static LicenseConditionResponse from(LicenseCondition condition) {
            Period periodicity = condition.getPeriodicity();
            int months = periodicity.getYears() * 12 + periodicity.getMonths();
            return new LicenseConditionResponse(condition.getName(), condition.getDocumentType(), months);
        }
    }
}
