package com.bioerp.biodesk.interfaces.api.alert;

import com.bioerp.biodesk.core.application.ListAlertsUseCase;
import com.bioerp.biodesk.core.application.MarkAlertAsReadUseCase;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final ListAlertsUseCase listAlertsUseCase;
    private final MarkAlertAsReadUseCase markAlertAsReadUseCase;

    public AlertController(ListAlertsUseCase listAlertsUseCase,
                           MarkAlertAsReadUseCase markAlertAsReadUseCase) {
        this.listAlertsUseCase = listAlertsUseCase;
        this.markAlertAsReadUseCase = markAlertAsReadUseCase;
    }

    @GetMapping
    public ResponseEntity<List<AlertResponse>> listAlerts() {
        List<AlertResponse> responses = listAlertsUseCase.handle().stream()
                .map(AlertResponse::from)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<AlertResponse> markAsRead(@PathVariable UUID id) {
        return markAlertAsReadUseCase.handle(id)
                .map(AlertResponse::from)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
