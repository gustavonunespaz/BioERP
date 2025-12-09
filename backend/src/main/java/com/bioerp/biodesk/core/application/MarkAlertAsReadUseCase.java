package com.bioerp.biodesk.core.application;

import com.bioerp.biodesk.core.domain.model.Alert;
import com.bioerp.biodesk.core.ports.AlertRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class MarkAlertAsReadUseCase {

    private final AlertRepository alertRepository;

    public MarkAlertAsReadUseCase(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public Optional<Alert> handle(UUID alertId) {
        return alertRepository.findById(alertId)
                .map(Alert::markAsRead)
                .map(alertRepository::save);
    }
}
