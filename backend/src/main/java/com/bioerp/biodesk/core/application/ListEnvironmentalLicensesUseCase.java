package com.bioerp.biodesk.core.application;

import com.bioerp.biodesk.core.domain.model.EnvironmentalLicense;
import com.bioerp.biodesk.core.ports.LicenseRepository;
import com.bioerp.biodesk.core.ports.query.LicenseSearchQuery;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ListEnvironmentalLicensesUseCase {

    private final LicenseRepository licenseRepository;

    public ListEnvironmentalLicensesUseCase(LicenseRepository licenseRepository) {
        this.licenseRepository = licenseRepository;
    }

    public List<EnvironmentalLicense> handle(LicenseSearchQuery query) {
        return licenseRepository.findAll(query);
    }
}
