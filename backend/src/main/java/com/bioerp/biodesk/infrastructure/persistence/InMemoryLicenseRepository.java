package com.bioerp.biodesk.infrastructure.persistence;

import com.bioerp.biodesk.core.domain.model.EnvironmentalLicense;
import com.bioerp.biodesk.core.ports.LicenseRepository;
import com.bioerp.biodesk.core.ports.query.LicenseSearchQuery;
import java.util.List;
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

    @Override
    public List<EnvironmentalLicense> findAll(LicenseSearchQuery query) {
        return storage.values().stream()
                .filter(license -> query.clientId() == null || query.clientId().equals(license.getClientId()))
                .filter(license -> query.unitId() == null || query.unitId().equals(license.getUnitId()))
                .toList();
    }
}
