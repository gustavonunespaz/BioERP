package com.bioerp.biodesk.core.application;

import com.bioerp.biodesk.core.application.ResourceConflictException;
import com.bioerp.biodesk.core.application.ResourceNotFoundException;
import com.bioerp.biodesk.core.domain.model.Client;
import com.bioerp.biodesk.core.domain.model.Unit;
import com.bioerp.biodesk.core.domain.service.CnpjValidator;
import com.bioerp.biodesk.core.ports.ClientRepository;
import com.bioerp.biodesk.core.ports.UnitRepository;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class CreateUnitUseCase {

    private final UnitRepository unitRepository;
    private final ClientRepository clientRepository;
    private final CnpjValidator cnpjValidator;

    public CreateUnitUseCase(UnitRepository unitRepository, ClientRepository clientRepository, CnpjValidator cnpjValidator) {
        this.unitRepository = unitRepository;
        this.clientRepository = clientRepository;
        this.cnpjValidator = cnpjValidator;
    }

    public Unit handle(Command command) {
        String normalizedCnpj = cnpjValidator.validateAndNormalize(command.cnpj());
        if (unitRepository.existsByCnpj(normalizedCnpj)) {
            throw new ResourceConflictException("Unit with this CNPJ already exists");
        }
        Client client = clientRepository.findById(command.clientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        Unit unit = Unit.builder()
                .id(command.id())
                .clientId(client.getId())
                .name(command.name())
                .cnpj(normalizedCnpj)
                .build();
        return unitRepository.save(unit);
    }

    public record Command(UUID id, UUID clientId, String name, String cnpj) {
    }
}
