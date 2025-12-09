package com.bioerp.biodesk.interfaces.api.license;

import com.bioerp.biodesk.core.application.ListLicenseDocumentVersionsUseCase;
import com.bioerp.biodesk.core.application.ListLicenseDocumentsUseCase;
import com.bioerp.biodesk.core.application.UploadLicenseDocumentVersionUseCase;
import com.bioerp.biodesk.core.domain.model.LicenseDocument;
import com.bioerp.biodesk.core.domain.model.LicenseDocumentVersion;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class LicenseDocumentController {

    private final UploadLicenseDocumentVersionUseCase uploadLicenseDocumentVersionUseCase;
    private final ListLicenseDocumentsUseCase listLicenseDocumentsUseCase;
    private final ListLicenseDocumentVersionsUseCase listLicenseDocumentVersionsUseCase;

    public LicenseDocumentController(UploadLicenseDocumentVersionUseCase uploadLicenseDocumentVersionUseCase,
                                     ListLicenseDocumentsUseCase listLicenseDocumentsUseCase,
                                     ListLicenseDocumentVersionsUseCase listLicenseDocumentVersionsUseCase) {
        this.uploadLicenseDocumentVersionUseCase = uploadLicenseDocumentVersionUseCase;
        this.listLicenseDocumentsUseCase = listLicenseDocumentsUseCase;
        this.listLicenseDocumentVersionsUseCase = listLicenseDocumentVersionsUseCase;
    }

    @PostMapping("/licenses/{licenseId}/documents/{tipo}/versions")
    public ResponseEntity<LicenseDocumentVersionResponse> uploadVersion(@PathVariable UUID licenseId,
                                                                        @PathVariable("tipo") String type,
                                                                        @RequestParam("file") MultipartFile file) {
        byte[] content;
        try {
            content = file.getBytes();
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to read file content", e);
        }
        LicenseDocumentVersion created = uploadLicenseDocumentVersionUseCase.handle(
                new UploadLicenseDocumentVersionUseCase.Command(
                        licenseId,
                        type,
                        file.getOriginalFilename(),
                        file.getContentType(),
                        content
                )
        );
        return ResponseEntity.ok(LicenseDocumentVersionResponse.from(created));
    }

    @GetMapping("/licenses/{licenseId}/documents")
    public ResponseEntity<List<LicenseDocumentResponse>> listDocuments(@PathVariable UUID licenseId) {
        List<LicenseDocument> documents = listLicenseDocumentsUseCase.handle(licenseId);
        List<LicenseDocumentResponse> response = documents.stream()
                .map(LicenseDocumentResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/documents/{documentId}/versions")
    public ResponseEntity<List<LicenseDocumentVersionResponse>> listVersions(@PathVariable UUID documentId) {
        List<LicenseDocumentVersion> versions = listLicenseDocumentVersionsUseCase.handle(documentId);
        List<LicenseDocumentVersionResponse> response = versions.stream()
                .map(LicenseDocumentVersionResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }
}
