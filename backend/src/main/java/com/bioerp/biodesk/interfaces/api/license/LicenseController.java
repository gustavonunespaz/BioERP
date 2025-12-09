package com.bioerp.biodesk.interfaces.api.license;

import com.bioerp.biodesk.core.application.GetEnvironmentalLicenseUseCase;
import com.bioerp.biodesk.core.application.ListEnvironmentalLicensesUseCase;
import com.bioerp.biodesk.core.application.RegisterEnvironmentalLicenseUseCase;
import com.bioerp.biodesk.core.domain.model.EnvironmentalLicense;
import com.bioerp.biodesk.core.domain.service.LicenseStatusEvaluator;
import com.bioerp.biodesk.core.ports.query.LicenseSearchQuery;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LicenseController {

    private final RegisterEnvironmentalLicenseUseCase registerEnvironmentalLicenseUseCase;
    private final ListEnvironmentalLicensesUseCase listEnvironmentalLicensesUseCase;
    private final GetEnvironmentalLicenseUseCase getEnvironmentalLicenseUseCase;
    private final LicenseStatusEvaluator licenseStatusEvaluator;

    public LicenseController(RegisterEnvironmentalLicenseUseCase registerEnvironmentalLicenseUseCase,
                             ListEnvironmentalLicensesUseCase listEnvironmentalLicensesUseCase,
                             GetEnvironmentalLicenseUseCase getEnvironmentalLicenseUseCase,
                             LicenseStatusEvaluator licenseStatusEvaluator) {
        this.registerEnvironmentalLicenseUseCase = registerEnvironmentalLicenseUseCase;
        this.listEnvironmentalLicensesUseCase = listEnvironmentalLicensesUseCase;
        this.getEnvironmentalLicenseUseCase = getEnvironmentalLicenseUseCase;
        this.licenseStatusEvaluator = licenseStatusEvaluator;
    }

    @PostMapping("/units/{unitId}/licenses")
    public ResponseEntity<LicenseResponse> createLicense(@PathVariable UUID unitId,
                                                         @Valid @RequestBody CreateEnvironmentalLicenseRequest request) {
        EnvironmentalLicense created = registerEnvironmentalLicenseUseCase.handle(new RegisterEnvironmentalLicenseUseCase.Command(
                null,
                unitId,
                request.name(),
                request.issuingAuthority(),
                request.issueDate(),
                request.expirationDate(),
                request.renewalLeadTimeDays(),
                Boolean.TRUE.equals(request.renewalRequested()),
                request.renewalRequestedAt(),
                request.toDomainConditions()
        ));
        LicenseResponse response = LicenseResponse.from(created, licenseStatusEvaluator);
        return ResponseEntity.created(URI.create("/api/licenses/" + response.id())).body(response);
    }

    @GetMapping("/licenses")
    public ResponseEntity<List<LicenseResponse>> listLicenses(@RequestParam(required = false) UUID clientId,
                                                              @RequestParam(required = false) UUID unitId) {
        LicenseSearchQuery query = new LicenseSearchQuery(clientId, unitId);
        List<LicenseResponse> response = listEnvironmentalLicensesUseCase.handle(query).stream()
                .map(license -> LicenseResponse.from(license, licenseStatusEvaluator))
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/licenses/{id}")
    public ResponseEntity<LicenseResponse> getLicense(@PathVariable UUID id) {
        return getEnvironmentalLicenseUseCase.handle(id)
                .map(license -> LicenseResponse.from(license, licenseStatusEvaluator))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
