package com.bioerp.biodesk.core.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.bioerp.biodesk.core.domain.model.Client;
import com.bioerp.biodesk.core.domain.service.CnpjValidator;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreateClientUseCaseTest {

    private InMemoryClientRepository clientRepository;
    private CreateClientUseCase useCase;

    @BeforeEach
    void setUp() {
        clientRepository = new InMemoryClientRepository();
        useCase = new CreateClientUseCase(clientRepository, new CnpjValidator());
    }

    @Test
    void shouldCreateClientWithValidCnpj() {
        CreateClientUseCase.Command command = defaultCommand("Bio Corp", "11222333000181");

        Client created = useCase.handle(command);

        assertEquals("Bio Corp", created.getName());
        assertEquals("11222333000181", created.getCnpj());
        assertNotNull(created.getCreatedAt());
    }

    @Test
    void shouldNotAllowDuplicatedCnpj() {
        useCase.handle(defaultCommand("Bio Corp", "04252011000110"));

        assertThrows(ResourceConflictException.class, () ->
                useCase.handle(defaultCommand("Other", "04252011000110"))
        );
    }

    @Test
    void shouldValidateCnpj() {
        CreateClientUseCase.Command command = defaultCommand("Bio Corp", "123");

        assertThrows(IllegalArgumentException.class, () -> useCase.handle(command));
    }

    private CreateClientUseCase.Command defaultCommand(String name, String cnpj) {
        return new CreateClientUseCase.Command(
                UUID.fromString("00000000-0000-0000-0000-000000000111"),
                name,
                name,
                cnpj,
                "Energia",
                "active",
                "Contato Padrão",
                "contato@bioerp.com",
                "11999990000",
                "Cliente criado para testes"
        );
    }
}
