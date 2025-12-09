package com.bioerp.biodesk.interfaces.api.alert;

import com.bioerp.biodesk.core.domain.model.Alert;
import com.bioerp.biodesk.core.domain.model.AlertType;
import java.time.LocalDateTime;
import java.util.UUID;

public record AlertResponse(UUID id,
                            UUID clientId,
                            UUID unitId,
                            UUID licenseId,
                            AlertType type,
                            String title,
                            String message,
                            boolean read,
                            LocalDateTime createdAt) {

    public static AlertResponse from(Alert alert) {
        return new AlertResponse(
                alert.getId(),
                alert.getClientId(),
                alert.getUnitId(),
                alert.getLicenseId(),
                alert.getType(),
                alert.getTitle(),
                alert.getMessage(),
                alert.isRead(),
                alert.getCreatedAt()
        );
    }
}
