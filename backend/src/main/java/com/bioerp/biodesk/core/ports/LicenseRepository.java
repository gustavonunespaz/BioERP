package com.bioerp.biodesk.core.ports;

import com.bioerp.biodesk.core.domain.model.EnvironmentalLicense;
import java.util.Optional;
import java.util.UUID;

public interface LicenseRepository {
    EnvironmentalLicense save(EnvironmentalLicense license);
    Optional<EnvironmentalLicense> findById(UUID id);
}
