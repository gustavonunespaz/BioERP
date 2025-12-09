package com.bioerp.biodesk.interfaces.api.license;

import com.bioerp.biodesk.core.domain.model.LicenseConditionStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateLicenseConditionStatusRequest(@NotNull LicenseConditionStatus status) {
}
