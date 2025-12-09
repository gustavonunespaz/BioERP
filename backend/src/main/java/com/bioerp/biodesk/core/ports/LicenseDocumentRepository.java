package com.bioerp.biodesk.core.ports;

import com.bioerp.biodesk.core.domain.model.LicenseDocument;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LicenseDocumentRepository {
    LicenseDocument save(LicenseDocument document);
    Optional<LicenseDocument> findById(UUID documentId);
    Optional<LicenseDocument> findByLicenseIdAndType(UUID licenseId, String type);
    List<LicenseDocument> findByLicenseId(UUID licenseId);
}
