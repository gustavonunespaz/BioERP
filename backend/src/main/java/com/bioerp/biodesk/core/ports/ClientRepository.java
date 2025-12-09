package com.bioerp.biodesk.core.ports;

import com.bioerp.biodesk.core.domain.model.Client;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientRepository {
    Client save(Client client);
    boolean existsByCnpj(String cnpj);
    Optional<Client> findById(UUID id);
    List<Client> findAll();
}
