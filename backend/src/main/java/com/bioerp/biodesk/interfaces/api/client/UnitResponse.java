package com.bioerp.biodesk.interfaces.api.client;

import com.bioerp.biodesk.core.domain.model.Unit;
import java.time.OffsetDateTime;
import java.util.UUID;

public record UnitResponse(
        UUID id,
        UUID clientId,
        String name,
        String cnpj,
        String addressLine,
        String city,
        String state,
        String activity,
        String notes,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
    public static UnitResponse from(Unit unit) {
        return new UnitResponse(
                unit.getId(),
                unit.getClientId(),
                unit.getName(),
                unit.getCnpj(),
                unit.getAddressLine(),
                unit.getCity(),
                unit.getState(),
                unit.getActivity(),
                unit.getNotes(),
                unit.getCreatedAt(),
                unit.getUpdatedAt()
        );
    }
}
