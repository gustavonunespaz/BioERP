package com.bioerp.biodesk.core.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.bioerp.biodesk.core.domain.model.EnvironmentalLicense;
import com.bioerp.biodesk.infrastructure.persistence.InMemoryLicenseRepository;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class GenerateAlertsUseCaseTest {

    private static final Clock FIXED_CLOCK = Clock.fixed(Instant.parse("2024-10-01T00:00:00Z"), ZoneOffset.UTC);

    @Test
    void shouldCreateAlertsForLicensesAndAvoidDuplicatesPerWindow() {
        InMemoryLicenseRepository licenseRepository = new InMemoryLicenseRepository();
        InMemoryAlertRepository alertRepository = new InMemoryAlertRepository();
        FakeAlertNotificationGateway notificationGateway = new FakeAlertNotificationGateway();

        EnvironmentalLicense license = EnvironmentalLicense.builder()
                .id(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                .clientId(UUID.fromString("22222222-2222-2222-2222-222222222222"))
                .unitId(UUID.fromString("33333333-3333-3333-3333-333333333333"))
                .name("Licença de Operação")
                .issuingAuthority("IBAMA")
                .issueDate(LocalDate.now(FIXED_CLOCK).minusYears(1))
                .expirationDate(LocalDate.now(FIXED_CLOCK).plusDays(45))
                .renewalLeadTimeDays(120)
                .renewalRequested(false)
                .conditions(java.util.Set.of())
                .build();

        licenseRepository.save(license);

        GenerateAlertsUseCase useCase = new GenerateAlertsUseCase(licenseRepository, alertRepository, notificationGateway, FIXED_CLOCK);

        useCase.handle();

        assertThat(alertRepository.findAll()).hasSize(1);
        assertThat(alertRepository.findAll().get(0).getDedupKey()).contains("due-in-60");
        assertThat(notificationGateway.getSentAlerts()).hasSize(1);

        useCase.handle();

        assertThat(alertRepository.findAll()).hasSize(1);
        assertThat(notificationGateway.getSentAlerts()).hasSize(1);
    }

    @Test
    void shouldCreateExpiredAlertWhenLicenseIsPastDue() {
        InMemoryLicenseRepository licenseRepository = new InMemoryLicenseRepository();
        InMemoryAlertRepository alertRepository = new InMemoryAlertRepository();
        FakeAlertNotificationGateway notificationGateway = new FakeAlertNotificationGateway();

        EnvironmentalLicense expiredLicense = EnvironmentalLicense.builder()
                .id(UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"))
                .clientId(UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"))
                .unitId(UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc"))
                .name("Licença Ambiental")
                .issuingAuthority("Secretaria Estadual")
                .issueDate(LocalDate.now(FIXED_CLOCK).minusYears(2))
                .expirationDate(LocalDate.now(FIXED_CLOCK).minusDays(1))
                .renewalLeadTimeDays(90)
                .renewalRequested(false)
                .conditions(java.util.Set.of())
                .build();

        licenseRepository.save(expiredLicense);

        GenerateAlertsUseCase useCase = new GenerateAlertsUseCase(licenseRepository, alertRepository, notificationGateway, FIXED_CLOCK);

        useCase.handle();

        assertThat(alertRepository.findAll())
                .singleElement()
                .satisfies(alert -> {
                    assertThat(alert.getDedupKey()).contains("expired");
                    assertThat(alert.getTitle()).isEqualTo("Licença vencida");
                });
    }
}
