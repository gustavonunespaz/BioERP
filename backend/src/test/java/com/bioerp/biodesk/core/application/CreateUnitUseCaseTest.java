package com.bioerp.biodesk.core.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.bioerp.biodesk.core.domain.model.Client;
import com.bioerp.biodesk.core.domain.model.Unit;
import com.bioerp.biodesk.core.domain.service.CnpjValidator;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreateUnitUseCaseTest {

    private InMemoryClientRepository clientRepository;
    private InMemoryUnitRepository unitRepository;
    private CreateUnitUseCase useCase;

    @BeforeEach
    void setUp() {
        clientRepository = new InMemoryClientRepository();
        unitRepository = new InMemoryUnitRepository();
        useCase = new CreateUnitUseCase(unitRepository, clientRepository, new CnpjValidator());
    }

    @Test
    void shouldCreateUnitForClient() {
        Client client = Client.builder().name("Bio Corp").cnpj("04252011000110").build();
        clientRepository.save(client);

        CreateUnitUseCase.Command command = new CreateUnitUseCase.Command(
                UUID.fromString("00000000-0000-0000-0000-000000000222"),
                client.getId(),
                "Filial Norte",
                "27865757000102"
        );

        Unit unit = useCase.handle(command);

        assertEquals(client.getId(), unit.getClientId());
        assertEquals("27865757000102", unit.getCnpj());
    }

    @Test
    void shouldNotAllowDuplicateUnitCnpj() {
        Client client = Client.builder().name("Bio Corp").cnpj("11222333000181").build();
        clientRepository.save(client);

        useCase.handle(new CreateUnitUseCase.Command(null, client.getId(), "Filial Norte", "19131243000197"));

        assertThrows(ResourceConflictException.class, () ->
                useCase.handle(new CreateUnitUseCase.Command(null, client.getId(), "Filial Sul", "19131243000197"))
        );
    }

    @Test
    void shouldValidateClientExistence() {
        assertThrows(ResourceNotFoundException.class, () ->
                useCase.handle(new CreateUnitUseCase.Command(null, UUID.randomUUID(), "Filial", "27865757000102"))
        );
    }
}
