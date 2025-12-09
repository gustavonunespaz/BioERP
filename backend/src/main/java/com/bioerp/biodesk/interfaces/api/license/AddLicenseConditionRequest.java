package com.bioerp.biodesk.interfaces.api.license;

import com.bioerp.biodesk.core.domain.model.LicenseCondition;
import com.bioerp.biodesk.core.domain.model.LicenseConditionStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.time.Period;

public record AddLicenseConditionRequest(
        @NotBlank String name,
        @NotBlank String documentType,
        @Positive int periodicityInMonths
) {
    public LicenseCondition toDomain() {
        return LicenseCondition.builder()
                .name(name)
                .documentType(documentType)
                .periodicity(Period.ofMonths(periodicityInMonths))
                .status(LicenseConditionStatus.PENDING)
                .build();
    }
}
