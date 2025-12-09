package com.bioerp.biodesk.core.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.bioerp.biodesk.core.domain.model.EnvironmentalLicense;
import com.bioerp.biodesk.core.domain.model.LicenseCondition;
import com.bioerp.biodesk.infrastructure.persistence.InMemoryLicenseRepository;
import java.time.LocalDate;
import java.time.Period;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegisterEnvironmentalLicenseUseCaseTest {

    private InMemoryLicenseRepository repository;
    private RegisterEnvironmentalLicenseUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = new InMemoryLicenseRepository();
        useCase = new RegisterEnvironmentalLicenseUseCase(repository);
    }

    @Test
    void shouldPersistLicenseWithConditions() {
        UUID licenseId = UUID.fromString("00000000-0000-0000-0000-000000000010");
        UUID clientId = UUID.fromString("00000000-0000-0000-0000-000000000099");

        LicenseCondition monitoring = LicenseCondition.builder()
                .name("Monitoramento atmosférico")
                .documentType("Relatório técnico")
                .periodicity(Period.ofMonths(6))
                .build();

        RegisterEnvironmentalLicenseUseCase.Command command = new RegisterEnvironmentalLicenseUseCase.Command(
                licenseId,
                clientId,
                "Licença Prévia",
                "IBAMA",
                LocalDate.of(2024, 1, 10),
                LocalDate.of(2025, 1, 10),
                120,
                true,
                LocalDate.of(2024, 9, 10),
                Set.of(monitoring)
        );

        EnvironmentalLicense saved = useCase.handle(command);

        assertEquals(licenseId, saved.getId());
        assertEquals(clientId, saved.getClientId());
        assertEquals("IBAMA", saved.getIssuingAuthority());
        assertEquals(1, saved.getConditions().size());
        assertTrue(repository.findById(licenseId).isPresent());
    }
}
