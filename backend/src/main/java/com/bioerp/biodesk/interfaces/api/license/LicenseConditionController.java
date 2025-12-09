package com.bioerp.biodesk.interfaces.api.license;

import com.bioerp.biodesk.core.application.AddLicenseConditionUseCase;
import com.bioerp.biodesk.core.application.UpdateLicenseConditionStatusUseCase;
import com.bioerp.biodesk.core.domain.model.EnvironmentalLicense;
import com.bioerp.biodesk.core.domain.model.LicenseConditionStatus;
import com.bioerp.biodesk.core.domain.service.LicenseStatusEvaluator;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LicenseConditionController {

    private final AddLicenseConditionUseCase addLicenseConditionUseCase;
    private final UpdateLicenseConditionStatusUseCase updateLicenseConditionStatusUseCase;
    private final LicenseStatusEvaluator licenseStatusEvaluator;

    public LicenseConditionController(AddLicenseConditionUseCase addLicenseConditionUseCase,
                                      UpdateLicenseConditionStatusUseCase updateLicenseConditionStatusUseCase,
                                      LicenseStatusEvaluator licenseStatusEvaluator) {
        this.addLicenseConditionUseCase = addLicenseConditionUseCase;
        this.updateLicenseConditionStatusUseCase = updateLicenseConditionStatusUseCase;
        this.licenseStatusEvaluator = licenseStatusEvaluator;
    }

    @PostMapping("/licenses/{licenseId}/conditions")
    public ResponseEntity<LicenseResponse> addCondition(@PathVariable UUID licenseId,
                                                        @Valid @RequestBody AddLicenseConditionRequest request) {
        EnvironmentalLicense updated = addLicenseConditionUseCase.handle(new AddLicenseConditionUseCase.Command(
                licenseId,
                null,
                request.name(),
                request.documentType(),
                java.time.Period.ofMonths(request.periodicityInMonths()),
                LicenseConditionStatus.PENDING
        ));
        LicenseResponse response = LicenseResponse.from(updated, licenseStatusEvaluator);
        return ResponseEntity.created(URI.create("/api/licenses/" + updated.getId())).body(response);
    }

    @PatchMapping("/conditions/{id}")
    public ResponseEntity<LicenseResponse> updateStatus(@PathVariable UUID id,
                                                        @Valid @RequestBody UpdateLicenseConditionStatusRequest request) {
        EnvironmentalLicense updated = updateLicenseConditionStatusUseCase.handle(
                new UpdateLicenseConditionStatusUseCase.Command(id, request.status())
        );
        LicenseResponse response = LicenseResponse.from(updated, licenseStatusEvaluator);
        return ResponseEntity.ok(response);
    }
}
