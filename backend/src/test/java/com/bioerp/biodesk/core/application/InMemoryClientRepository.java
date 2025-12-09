package com.bioerp.biodesk.core.application;

import com.bioerp.biodesk.core.domain.model.Client;
import com.bioerp.biodesk.core.ports.ClientRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

class InMemoryClientRepository implements ClientRepository {

    private final Map<UUID, Client> storage = new HashMap<>();

    @Override
    public Client save(Client client) {
        storage.put(client.getId(), client);
        return client;
    }

    @Override
    public boolean existsByCnpj(String cnpj) {
        return storage.values().stream().anyMatch(client -> client.getCnpj().equals(cnpj));
    }

    @Override
    public Optional<Client> findById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Client> findAll() {
        return new ArrayList<>(storage.values());
    }
}
