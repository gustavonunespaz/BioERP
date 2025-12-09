package com.bioerp.biodesk.interfaces.api.license;

import com.bioerp.biodesk.core.domain.model.LicenseDocumentVersion;
import java.time.Instant;
import java.util.UUID;

public record LicenseDocumentVersionResponse(UUID id,
                                            int versionNumber,
                                            String fileName,
                                            String contentType,
                                            Instant uploadedAt,
                                            boolean active) {

    public static LicenseDocumentVersionResponse from(LicenseDocumentVersion version) {
        return new LicenseDocumentVersionResponse(
                version.getId(),
                version.getVersionNumber(),
                version.getFileName(),
                version.getContentType(),
                version.getUploadedAt(),
                version.isActive()
        );
    }
}
