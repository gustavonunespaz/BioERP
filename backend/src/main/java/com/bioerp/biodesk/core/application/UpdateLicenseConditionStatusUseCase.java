package com.bioerp.biodesk.core.application;

import com.bioerp.biodesk.core.domain.model.EnvironmentalLicense;
import com.bioerp.biodesk.core.domain.model.LicenseConditionStatus;
import com.bioerp.biodesk.core.ports.LicenseRepository;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class UpdateLicenseConditionStatusUseCase {

    private final LicenseRepository licenseRepository;

    public UpdateLicenseConditionStatusUseCase(LicenseRepository licenseRepository) {
        this.licenseRepository = licenseRepository;
    }

    public EnvironmentalLicense handle(Command command) {
        EnvironmentalLicense license = licenseRepository.findByConditionId(command.conditionId())
                .orElseThrow(() -> new ResourceNotFoundException("Condition not found"));

        EnvironmentalLicense updated = license.updateConditionStatus(command.conditionId(), command.status());
        return licenseRepository.save(updated);
    }

    public record Command(UUID conditionId, LicenseConditionStatus status) {
    }
}
