package com.bioerp.biodesk.core.ports;

import com.bioerp.biodesk.core.domain.model.EnvironmentalLicense;
import com.bioerp.biodesk.core.ports.query.LicenseSearchQuery;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LicenseRepository {
    EnvironmentalLicense save(EnvironmentalLicense license);
    Optional<EnvironmentalLicense> findById(UUID id);
    List<EnvironmentalLicense> findAll(LicenseSearchQuery query);
}
