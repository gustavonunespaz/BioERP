package com.bioerp.biodesk.interfaces.api.client;

import com.bioerp.biodesk.core.domain.model.Client;
import java.time.OffsetDateTime;
import java.util.UUID;

public record ClientResponse(
        UUID id,
        String name,
        String cnpj,
        OffsetDateTime createdAt
) {
    public static ClientResponse from(Client client) {
        return new ClientResponse(client.getId(), client.getName(), client.getCnpj(), client.getCreatedAt());
    }
}
