package com.bioerp.biodesk.core.application;

import com.bioerp.biodesk.core.application.ResourceNotFoundException;
import com.bioerp.biodesk.core.domain.model.Client;
import com.bioerp.biodesk.core.domain.model.Unit;
import com.bioerp.biodesk.core.ports.ClientRepository;
import com.bioerp.biodesk.core.ports.UnitRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ListUnitsByClientUseCase {

    private final UnitRepository unitRepository;
    private final ClientRepository clientRepository;

    public ListUnitsByClientUseCase(UnitRepository unitRepository, ClientRepository clientRepository) {
        this.unitRepository = unitRepository;
        this.clientRepository = clientRepository;
    }

    public List<Unit> handle(UUID clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        return unitRepository.findByClientId(client.getId());
    }
}
