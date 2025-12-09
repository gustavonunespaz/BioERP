package com.bioerp.biodesk.infrastructure.notification;

import com.bioerp.biodesk.core.domain.model.Alert;
import com.bioerp.biodesk.core.ports.AlertNotificationGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingAlertNotificationGateway implements AlertNotificationGateway {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAlertNotificationGateway.class);

    @Override
    public void notify(Alert alert) {
        LOGGER.info("[Mock Email] Novo alerta enviado: {} - {}", alert.getTitle(), alert.getMessage());
    }
}
