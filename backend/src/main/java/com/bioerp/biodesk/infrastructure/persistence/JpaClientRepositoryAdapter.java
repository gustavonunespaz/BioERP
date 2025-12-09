package com.bioerp.biodesk.infrastructure.persistence;

import com.bioerp.biodesk.core.domain.model.Client;
import com.bioerp.biodesk.core.ports.ClientRepository;
import com.bioerp.biodesk.infrastructure.persistence.jpa.ClientJpaEntity;
import com.bioerp.biodesk.infrastructure.persistence.jpa.ClientJpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class JpaClientRepositoryAdapter implements ClientRepository {

    private final ClientJpaRepository clientJpaRepository;

    public JpaClientRepositoryAdapter(ClientJpaRepository clientJpaRepository) {
        this.clientJpaRepository = clientJpaRepository;
    }

    @Override
    public Client save(Client client) {
        ClientJpaEntity entity = ClientJpaEntity.fromDomain(client);
        return clientJpaRepository.save(entity).toDomain();
    }

    @Override
    public boolean existsByCnpj(String cnpj) {
        return clientJpaRepository.existsByCnpj(cnpj);
    }

    @Override
    public Optional<Client> findById(UUID id) {
        return clientJpaRepository.findById(id).map(ClientJpaEntity::toDomain);
    }

    @Override
    public List<Client> findAll() {
        return clientJpaRepository.findAll().stream().map(ClientJpaEntity::toDomain).toList();
    }
}
