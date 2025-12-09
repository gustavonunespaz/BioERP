package com.bioerp.biodesk.interfaces.api.license;

import com.bioerp.biodesk.core.domain.model.LicenseDocument;
import java.util.UUID;

public record LicenseDocumentResponse(UUID id,
                                     UUID licenseId,
                                     String type,
                                     LicenseDocumentVersionResponse activeVersion) {

    public static LicenseDocumentResponse from(LicenseDocument document) {
        return new LicenseDocumentResponse(
                document.getId(),
                document.getLicenseId(),
                document.getType(),
                document.getActiveVersion().map(LicenseDocumentVersionResponse::from).orElse(null)
        );
    }
}
