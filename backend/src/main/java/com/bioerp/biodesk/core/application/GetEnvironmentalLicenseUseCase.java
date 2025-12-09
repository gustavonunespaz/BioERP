package com.bioerp.biodesk.core.application;

import com.bioerp.biodesk.core.domain.model.EnvironmentalLicense;
import com.bioerp.biodesk.core.ports.LicenseRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class GetEnvironmentalLicenseUseCase {

    private final LicenseRepository licenseRepository;

    public GetEnvironmentalLicenseUseCase(LicenseRepository licenseRepository) {
        this.licenseRepository = licenseRepository;
    }

    public Optional<EnvironmentalLicense> handle(UUID id) {
        return licenseRepository.findById(id);
    }
}
