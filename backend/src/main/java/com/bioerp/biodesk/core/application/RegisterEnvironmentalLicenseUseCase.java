package com.bioerp.biodesk.core.application;

import com.bioerp.biodesk.core.application.ResourceNotFoundException;
import com.bioerp.biodesk.core.domain.model.EnvironmentalLicense;
import com.bioerp.biodesk.core.domain.model.LicenseCondition;
import com.bioerp.biodesk.core.domain.model.Unit;
import com.bioerp.biodesk.core.ports.LicenseRepository;
import com.bioerp.biodesk.core.ports.UnitRepository;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class RegisterEnvironmentalLicenseUseCase {

    private final LicenseRepository licenseRepository;
    private final UnitRepository unitRepository;

    public RegisterEnvironmentalLicenseUseCase(LicenseRepository licenseRepository, UnitRepository unitRepository) {
        this.licenseRepository = licenseRepository;
        this.unitRepository = unitRepository;
    }

    public EnvironmentalLicense handle(Command command) {
        Unit unit = unitRepository.findById(command.unitId())
                .orElseThrow(() -> new ResourceNotFoundException("Unit not found"));
        EnvironmentalLicense license = EnvironmentalLicense.builder()
                .id(command.id())
                .clientId(unit.getClientId())
                .unitId(unit.getId())
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
            UUID unitId,
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
