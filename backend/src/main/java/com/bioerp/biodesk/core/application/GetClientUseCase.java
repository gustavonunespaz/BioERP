package com.bioerp.biodesk.core.application;

import com.bioerp.biodesk.core.domain.model.Client;
import com.bioerp.biodesk.core.ports.ClientRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class GetClientUseCase {

    private final ClientRepository clientRepository;

    public GetClientUseCase(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Optional<Client> handle(UUID id) {
        return clientRepository.findById(id);
    }
}
