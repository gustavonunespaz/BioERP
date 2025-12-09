package com.bioerp.biodesk.core.application;

import com.bioerp.biodesk.core.domain.model.EnvironmentalLicense;
import com.bioerp.biodesk.core.domain.model.LicenseCondition;
import com.bioerp.biodesk.core.ports.LicenseRepository;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public class RegisterEnvironmentalLicenseUseCase {

    private final LicenseRepository licenseRepository;

    public RegisterEnvironmentalLicenseUseCase(LicenseRepository licenseRepository) {
        this.licenseRepository = licenseRepository;
    }

    public EnvironmentalLicense handle(Command command) {
        EnvironmentalLicense license = EnvironmentalLicense.builder()
                .id(command.id())
                .clientId(command.clientId())
                .name(command.name())
                .issuingAuthority(command.issuingAuthority())
                .issueDate(command.issueDate())
                .expirationDate(command.expirationDate())
                .renewalLeadTimeDays(command.renewalLeadTimeDays())
                .renewalRequested(command.renewalRequested())
                .renewalRequestedAt(command.renewalRequestedAt())
                .conditions(command.conditions())
                .build();
        return licenseRepository.save(license);
    }

    public record Command(
            UUID id,
            UUID clientId,
            String name,
            String issuingAuthority,
            LocalDate issueDate,
            LocalDate expirationDate,
            int renewalLeadTimeDays,
            boolean renewalRequested,
            LocalDate renewalRequestedAt,
            Set<LicenseCondition> conditions
    ) {
    }
}
