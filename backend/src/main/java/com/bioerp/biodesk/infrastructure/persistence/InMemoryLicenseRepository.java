package com.bioerp.biodesk.infrastructure.persistence;

import com.bioerp.biodesk.core.domain.model.EnvironmentalLicense;
import com.bioerp.biodesk.core.ports.LicenseRepository;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryLicenseRepository implements LicenseRepository {

    private final Map<UUID, EnvironmentalLicense> storage = new ConcurrentHashMap<>();

    @Override
    public EnvironmentalLicense save(EnvironmentalLicense license) {
        storage.put(license.getId(), license);
        return license;
    }

    @Override
    public Optional<EnvironmentalLicense> findById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }
}
