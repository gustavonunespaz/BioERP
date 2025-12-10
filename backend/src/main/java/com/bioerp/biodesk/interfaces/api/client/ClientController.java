package com.bioerp.biodesk.interfaces.api.client;

import com.bioerp.biodesk.core.application.CreateClientUseCase;
import com.bioerp.biodesk.core.application.CreateUnitUseCase;
import com.bioerp.biodesk.core.application.GetClientUseCase;
import com.bioerp.biodesk.core.application.GetUnitUseCase;
import com.bioerp.biodesk.core.application.ListClientsUseCase;
import com.bioerp.biodesk.core.application.ListUnitsByClientUseCase;
import com.bioerp.biodesk.core.domain.model.Client;
import com.bioerp.biodesk.core.domain.model.Unit;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ClientController {

    private final CreateClientUseCase createClientUseCase;
    private final ListClientsUseCase listClientsUseCase;
    private final GetClientUseCase getClientUseCase;
    private final CreateUnitUseCase createUnitUseCase;
    private final ListUnitsByClientUseCase listUnitsByClientUseCase;
    private final GetUnitUseCase getUnitUseCase;

    public ClientController(CreateClientUseCase createClientUseCase,
                            ListClientsUseCase listClientsUseCase,
                            GetClientUseCase getClientUseCase,
                            CreateUnitUseCase createUnitUseCase,
                            ListUnitsByClientUseCase listUnitsByClientUseCase,
                            GetUnitUseCase getUnitUseCase) {
        this.createClientUseCase = createClientUseCase;
        this.listClientsUseCase = listClientsUseCase;
        this.getClientUseCase = getClientUseCase;
        this.createUnitUseCase = createUnitUseCase;
        this.listUnitsByClientUseCase = listUnitsByClientUseCase;
        this.getUnitUseCase = getUnitUseCase;
    }

    @PostMapping("/clients")
    public ResponseEntity<ClientResponse> createClient(@Valid @RequestBody CreateClientRequest request) {
        Client created = createClientUseCase.handle(new CreateClientUseCase.Command(
                null,
                request.name(),
                request.tradeName(),
                request.cnpj(),
                request.segment(),
                request.status(),
                request.mainContactName(),
                request.mainContactEmail(),
                request.mainContactPhone(),
                request.notes()
        ));
        ClientResponse response = ClientResponse.from(created);
        return ResponseEntity.created(URI.create("/api/clients/" + response.id())).body(response);
    }

    @GetMapping("/clients")
    public ResponseEntity<List<ClientResponse>> listClients() {
        List<ClientResponse> response = listClientsUseCase.handle().stream()
                .map(ClientResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<ClientResponse> getClient(@PathVariable UUID id) {
        return getClientUseCase.handle(id)
                .map(ClientResponse::from)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/clients/{clientId}/units")
    public ResponseEntity<UnitResponse> createUnit(@PathVariable UUID clientId, @Valid @RequestBody CreateUnitRequest request) {
        Unit created = createUnitUseCase.handle(new CreateUnitUseCase.Command(
                null,
                clientId,
                request.name(),
                request.cnpj(),
                request.addressLine(),
                request.city(),
                request.state(),
                request.activity(),
                request.notes()
        ));
        UnitResponse response = UnitResponse.from(created);
        return ResponseEntity.created(URI.create("/api/units/" + response.id())).body(response);
    }

    @GetMapping("/clients/{clientId}/units")
    public ResponseEntity<List<UnitResponse>> listUnits(@PathVariable UUID clientId) {
        List<UnitResponse> response = listUnitsByClientUseCase.handle(clientId).stream()
                .map(UnitResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/units/{id}")
    public ResponseEntity<UnitResponse> getUnit(@PathVariable UUID id) {
        return getUnitUseCase.handle(id)
                .map(UnitResponse::from)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
