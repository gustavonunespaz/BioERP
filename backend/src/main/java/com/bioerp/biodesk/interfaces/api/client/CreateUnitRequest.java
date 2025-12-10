package com.bioerp.biodesk.interfaces.api.client;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUnitRequest(
        @NotBlank @Size(max = 255) String name,
        @NotBlank @Size(max = 20) String cnpj,
        @Size(max = 255) String addressLine,
        @Size(max = 128) String city,
        @Size(max = 64) String state,
        @Size(max = 255) String activity,
        String notes
) {
}
