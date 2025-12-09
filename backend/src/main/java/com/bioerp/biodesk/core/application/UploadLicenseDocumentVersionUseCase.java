package com.bioerp.biodesk.core.application;

import com.bioerp.biodesk.core.domain.model.LicenseDocument;
import com.bioerp.biodesk.core.domain.model.LicenseDocumentVersion;
import com.bioerp.biodesk.core.ports.AuditLogger;
import com.bioerp.biodesk.core.ports.LicenseDocumentRepository;
import com.bioerp.biodesk.core.ports.LicenseRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class UploadLicenseDocumentVersionUseCase {

    private final LicenseDocumentRepository licenseDocumentRepository;
    private final LicenseRepository licenseRepository;
    private final AuditLogger auditLogger;

    public UploadLicenseDocumentVersionUseCase(LicenseDocumentRepository licenseDocumentRepository,
                                               LicenseRepository licenseRepository,
                                               AuditLogger auditLogger) {
        this.licenseDocumentRepository = licenseDocumentRepository;
        this.licenseRepository = licenseRepository;
        this.auditLogger = auditLogger;
    }

    public LicenseDocumentVersion handle(Command command) {
        licenseRepository.findById(command.licenseId())
                .orElseThrow(() -> new ResourceNotFoundException("License not found"));
        Optional<LicenseDocument> existingDocument = licenseDocumentRepository
                .findByLicenseIdAndType(command.licenseId(), command.type());
        LicenseDocument document = existingDocument
                .orElseGet(() -> LicenseDocument.create(command.licenseId(), command.type()));
        LicenseDocumentVersion previousActive = document.getActiveVersion().orElse(null);
        LicenseDocument updated = document.addNewVersion(command.fileName(), command.contentType(), command.fileData());
        LicenseDocumentVersion newVersion = updated.getActiveVersion()
                .orElseThrow(() -> new IllegalStateException("Active version not found after upload"));
        licenseDocumentRepository.save(updated);
        auditLogger.log("LICENSE_DOCUMENT_UPLOAD",
                "Uploaded version %d for document %s of license %s".formatted(
                        newVersion.getVersionNumber(), updated.getId(), updated.getLicenseId()));
        if (previousActive != null && !previousActive.getId().equals(newVersion.getId())) {
            auditLogger.log("LICENSE_DOCUMENT_VERSION_SWITCH",
                    "Deactivated version %d and activated version %d for document %s".formatted(
                            previousActive.getVersionNumber(), newVersion.getVersionNumber(), updated.getId()));
        }
        return newVersion;
    }

    public record Command(UUID licenseId, String type, String fileName, String contentType, byte[] fileData) {
    }
}
