package com.bioerp.biodesk.interfaces.api.client;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateClientRequest(
        @NotBlank @Size(max = 255) String name,
        @Size(max = 255) String tradeName,
        @NotBlank @Size(max = 20) String cnpj,
        @Size(max = 128) String segment,
        @Size(max = 32) String status,
        @Size(max = 255) String mainContactName,
        @Size(max = 255) String mainContactEmail,
        @Size(max = 64) String mainContactPhone,
        String notes
) {
}
