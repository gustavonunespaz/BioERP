package com.bioerp.biodesk.interfaces.api.client;

import com.bioerp.biodesk.core.domain.model.Client;
import java.time.OffsetDateTime;
import java.util.UUID;

public record ClientResponse(
        UUID id,
        String name,
        String tradeName,
        String cnpj,
        String segment,
        String status,
        String mainContactName,
        String mainContactEmail,
        String mainContactPhone,
        String notes,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
    public static ClientResponse from(Client client) {
        return new ClientResponse(
                client.getId(),
                client.getName(),
                client.getTradeName(),
                client.getCnpj(),
                client.getSegment(),
                client.getStatus(),
                client.getMainContactName(),
                client.getMainContactEmail(),
                client.getMainContactPhone(),
                client.getNotes(),
                client.getCreatedAt(),
                client.getUpdatedAt()
        );
    }
}
