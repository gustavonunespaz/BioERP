package com.bioerp.biodesk.core.application;

import com.bioerp.biodesk.core.domain.model.Alert;
import com.bioerp.biodesk.core.ports.AlertRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ListAlertsUseCase {

    private final AlertRepository alertRepository;

    public ListAlertsUseCase(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public List<Alert> handle() {
        return alertRepository.findAll();
    }
}
