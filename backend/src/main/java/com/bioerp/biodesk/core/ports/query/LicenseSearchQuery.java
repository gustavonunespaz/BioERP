package com.bioerp.biodesk.core.ports.query;

import java.util.UUID;

public record LicenseSearchQuery(UUID clientId, UUID unitId) {
}
