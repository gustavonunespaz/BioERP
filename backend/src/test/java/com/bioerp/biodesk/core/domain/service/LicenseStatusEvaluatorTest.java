package com.bioerp.biodesk.core.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.bioerp.biodesk.core.domain.model.EnvironmentalLicense;
import com.bioerp.biodesk.core.domain.model.LicenseLifecycleStatus;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LicenseStatusEvaluatorTest {

    private Clock clock;
    private LicenseStatusEvaluator evaluator;
    private EnvironmentalLicense baseLicense;

    @BeforeEach
    void setUp() {
        LocalDate today = LocalDate.of(2024, 10, 1);
        clock = Clock.fixed(today.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        evaluator = new LicenseStatusEvaluator(clock);
        baseLicense = EnvironmentalLicense.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .clientId(UUID.fromString("00000000-0000-0000-0000-000000000002"))
                .unitId(UUID.fromString("00000000-0000-0000-0000-000000000003"))
                .name("Licença de Operação")
                .issuingAuthority("CETESB")
                .issueDate(LocalDate.of(2023, 10, 1))
                .expirationDate(LocalDate.of(2025, 3, 1))
                .renewalLeadTimeDays(120)
                .renewalRequested(false)
                .build();
    }

    @Test
    void shouldBeVigenteOutsideRenewalWindow() {
        LicenseLifecycleStatus status = evaluator.evaluate(baseLicense);
        assertEquals(LicenseLifecycleStatus.VIGENTE, status);
    }

    @Test
    void shouldBeRenovacaoAtrasadaInsideWindowWithoutRenewalRequest() {
        EnvironmentalLicense license = EnvironmentalLicense.builder()
                .id(baseLicense.getId())
                .clientId(baseLicense.getClientId())
                .unitId(baseLicense.getUnitId())
                .name(baseLicense.getName())
                .issuingAuthority(baseLicense.getIssuingAuthority())
                .issueDate(baseLicense.getIssueDate())
                .expirationDate(LocalDate.of(2024, 11, 15))
                .renewalLeadTimeDays(120)
                .renewalRequested(false)
                .build();

        LicenseLifecycleStatus status = evaluator.evaluate(license);
        assertEquals(LicenseLifecycleStatus.RENOVACAO_ATRASADA, status);
    }

    @Test
    void shouldBeEmRenovacaoWhenRequestExists() {
        EnvironmentalLicense license = baseLicense.requestRenewal(LocalDate.of(2024, 9, 1));
        LicenseLifecycleStatus status = evaluator.evaluate(license);
        assertEquals(LicenseLifecycleStatus.EM_RENOVACAO, status);
    }

    @Test
    void shouldBeVencidaAfterExpirationDate() {
        EnvironmentalLicense license = EnvironmentalLicense.builder()
                .id(baseLicense.getId())
                .clientId(baseLicense.getClientId())
                .unitId(baseLicense.getUnitId())
                .name(baseLicense.getName())
                .issuingAuthority(baseLicense.getIssuingAuthority())
                .issueDate(baseLicense.getIssueDate())
                .expirationDate(LocalDate.of(2024, 9, 1))
                .renewalLeadTimeDays(120)
                .renewalRequested(true)
                .renewalRequestedAt(LocalDate.of(2024, 6, 1))
                .build();

        LicenseLifecycleStatus status = evaluator.evaluate(license);
        assertEquals(LicenseLifecycleStatus.VENCIDA, status);
    }
}
