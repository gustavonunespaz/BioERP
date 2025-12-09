package com.bioerp.biodesk.core.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.bioerp.biodesk.core.domain.model.EnvironmentalLicense;
import com.bioerp.biodesk.core.domain.model.LicenseCondition;
import com.bioerp.biodesk.core.domain.model.Unit;
import com.bioerp.biodesk.infrastructure.persistence.InMemoryLicenseRepository;
import java.time.LocalDate;
import java.time.Period;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegisterEnvironmentalLicenseUseCaseTest {

    private InMemoryLicenseRepository repository;
    private InMemoryUnitRepository unitRepository;
    private RegisterEnvironmentalLicenseUseCase useCase;
    private UUID unitId;
    private UUID clientId;

    @BeforeEach
    void setUp() {
        repository = new InMemoryLicenseRepository();
        unitRepository = new InMemoryUnitRepository();
        useCase = new RegisterEnvironmentalLicenseUseCase(repository, unitRepository);
        clientId = UUID.fromString("00000000-0000-0000-0000-000000000099");
        unitId = UUID.fromString("00000000-0000-0000-0000-000000000098");
        unitRepository.save(Unit.builder()
                .id(unitId)
                .clientId(clientId)
                .name("Unit A")
                .cnpj("12345678000190")
                .build());
    }

    @Test
    void shouldPersistLicenseWithConditions() {
        UUID licenseId = UUID.fromString("00000000-0000-0000-0000-000000000010");

        LicenseCondition monitoring = LicenseCondition.builder()
                .name("Monitoramento atmosférico")
                .documentType("Relatório técnico")
                .periodicity(Period.ofMonths(6))
                .build();

        RegisterEnvironmentalLicenseUseCase.Command command = new RegisterEnvironmentalLicenseUseCase.Command(
                licenseId,
                unitId,
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
        assertEquals(unitId, saved.getUnitId());
        assertEquals("IBAMA", saved.getIssuingAuthority());
        assertEquals(1, saved.getConditions().size());
        assertTrue(repository.findById(licenseId).isPresent());
    }
}
