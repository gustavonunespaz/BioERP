package com.bioerp.biodesk.core.ports;

import com.bioerp.biodesk.core.domain.model.Alert;

public interface AlertNotificationGateway {
    void notify(Alert alert);
}
