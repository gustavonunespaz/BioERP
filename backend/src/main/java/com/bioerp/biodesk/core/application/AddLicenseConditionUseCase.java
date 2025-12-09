package com.bioerp.biodesk.core.application;

import com.bioerp.biodesk.core.domain.model.EnvironmentalLicense;
import com.bioerp.biodesk.core.domain.model.LicenseCondition;
import com.bioerp.biodesk.core.domain.model.LicenseConditionStatus;
import com.bioerp.biodesk.core.ports.LicenseRepository;
import java.time.Period;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class AddLicenseConditionUseCase {

    private final LicenseRepository licenseRepository;

    public AddLicenseConditionUseCase(LicenseRepository licenseRepository) {
        this.licenseRepository = licenseRepository;
    }

    public EnvironmentalLicense handle(Command command) {
        EnvironmentalLicense license = licenseRepository.findById(command.licenseId())
                .orElseThrow(() -> new ResourceNotFoundException("License not found"));

        LicenseCondition condition = LicenseCondition.builder()
                .id(command.conditionId())
                .name(command.name())
                .documentType(command.documentType())
                .periodicity(command.periodicity())
                .status(command.status())
                .build();

        EnvironmentalLicense updated = license.addCondition(condition);
        return licenseRepository.save(updated);
    }

    public record Command(
            UUID licenseId,
            UUID conditionId,
            String name,
            String documentType,
            Period periodicity,
            LicenseConditionStatus status
    ) {
    }
}
