package com.bioerp.biodesk.interfaces.api.client;

import jakarta.validation.constraints.NotBlank;

public record CreateUnitRequest(
        @NotBlank String name,
        @NotBlank String cnpj
) {
}
