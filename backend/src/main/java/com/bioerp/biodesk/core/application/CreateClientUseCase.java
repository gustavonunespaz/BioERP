package com.bioerp.biodesk.core.application;

import com.bioerp.biodesk.core.domain.model.Client;
import com.bioerp.biodesk.core.domain.service.CnpjValidator;
import com.bioerp.biodesk.core.ports.ClientRepository;
import com.bioerp.biodesk.core.application.ResourceConflictException;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class CreateClientUseCase {

    private final ClientRepository clientRepository;
    private final CnpjValidator cnpjValidator;

    public CreateClientUseCase(ClientRepository clientRepository, CnpjValidator cnpjValidator) {
        this.clientRepository = clientRepository;
        this.cnpjValidator = cnpjValidator;
    }

    public Client handle(Command command) {
        String normalizedCnpj = cnpjValidator.validateAndNormalize(command.cnpj());
        if (clientRepository.existsByCnpj(normalizedCnpj)) {
            throw new ResourceConflictException("Client with this CNPJ already exists");
        }
        Client client = Client.builder()
                .id(command.id())
                .name(command.name())
                .tradeName(command.tradeName())
                .cnpj(normalizedCnpj)
                .segment(command.segment())
                .status(command.status())
                .mainContactName(command.mainContactName())
                .mainContactEmail(command.mainContactEmail())
                .mainContactPhone(command.mainContactPhone())
                .notes(command.notes())
                .build();
        return clientRepository.save(client);
    }

    public record Command(UUID id,
                          String name,
                          String tradeName,
                          String cnpj,
                          String segment,
                          String status,
                          String mainContactName,
                          String mainContactEmail,
                          String mainContactPhone,
                          String notes) {
    }
}
