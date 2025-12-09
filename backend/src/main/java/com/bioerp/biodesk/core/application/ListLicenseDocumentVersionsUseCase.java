package com.bioerp.biodesk.core.application;

import com.bioerp.biodesk.core.domain.model.LicenseDocument;
import com.bioerp.biodesk.core.domain.model.LicenseDocumentVersion;
import com.bioerp.biodesk.core.ports.LicenseDocumentRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ListLicenseDocumentVersionsUseCase {

    private final LicenseDocumentRepository licenseDocumentRepository;

    public ListLicenseDocumentVersionsUseCase(LicenseDocumentRepository licenseDocumentRepository) {
        this.licenseDocumentRepository = licenseDocumentRepository;
    }

    public List<LicenseDocumentVersion> handle(UUID documentId) {
        LicenseDocument document = licenseDocumentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found"));
        return document.getVersions();
    }
}
