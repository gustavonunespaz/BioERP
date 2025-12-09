package com.bioerp.biodesk.core.application;

import com.bioerp.biodesk.core.domain.model.Client;
import com.bioerp.biodesk.core.ports.ClientRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ListClientsUseCase {

    private final ClientRepository clientRepository;

    public ListClientsUseCase(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> handle() {
        return clientRepository.findAll();
    }
}
