package com.bioerp.biodesk.core.application;

import com.bioerp.biodesk.core.domain.model.Alert;
import com.bioerp.biodesk.core.ports.AlertNotificationGateway;
import java.util.ArrayList;
import java.util.List;

public class FakeAlertNotificationGateway implements AlertNotificationGateway {

    private final List<Alert> sentAlerts = new ArrayList<>();

    @Override
    public void notify(Alert alert) {
        sentAlerts.add(alert);
    }

    public List<Alert> getSentAlerts() {
        return List.copyOf(sentAlerts);
    }
}
