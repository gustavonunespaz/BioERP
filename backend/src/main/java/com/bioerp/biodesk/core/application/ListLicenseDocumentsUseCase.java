package com.bioerp.biodesk.core.application;

import com.bioerp.biodesk.core.domain.model.LicenseDocument;
import com.bioerp.biodesk.core.ports.LicenseDocumentRepository;
import com.bioerp.biodesk.core.ports.LicenseRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ListLicenseDocumentsUseCase {

    private final LicenseDocumentRepository licenseDocumentRepository;
    private final LicenseRepository licenseRepository;

    public ListLicenseDocumentsUseCase(LicenseDocumentRepository licenseDocumentRepository,
                                       LicenseRepository licenseRepository) {
        this.licenseDocumentRepository = licenseDocumentRepository;
        this.licenseRepository = licenseRepository;
    }

    public List<LicenseDocument> handle(UUID licenseId) {
        licenseRepository.findById(licenseId)
                .orElseThrow(() -> new ResourceNotFoundException("License not found"));
        return licenseDocumentRepository.findByLicenseId(licenseId);
    }
}
