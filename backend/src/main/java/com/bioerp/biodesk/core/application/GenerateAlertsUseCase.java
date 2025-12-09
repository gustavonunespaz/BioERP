package com.bioerp.biodesk.core.application;

import com.bioerp.biodesk.core.domain.model.Alert;
import com.bioerp.biodesk.core.domain.model.AlertType;
import com.bioerp.biodesk.core.domain.model.EnvironmentalLicense;
import com.bioerp.biodesk.core.ports.AlertNotificationGateway;
import com.bioerp.biodesk.core.ports.AlertRepository;
import com.bioerp.biodesk.core.ports.LicenseRepository;
import com.bioerp.biodesk.core.ports.query.LicenseSearchQuery;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Service;

@Service
public class GenerateAlertsUseCase {

    private static final List<Integer> ALERT_WINDOWS = List.of(30, 60, 90);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.forLanguageTag("pt-BR"));

    private final LicenseRepository licenseRepository;
    private final AlertRepository alertRepository;
    private final AlertNotificationGateway alertNotificationGateway;
    private final Clock clock;

    public GenerateAlertsUseCase(LicenseRepository licenseRepository,
                                 AlertRepository alertRepository,
                                 AlertNotificationGateway alertNotificationGateway,
                                 Clock clock) {
        this.licenseRepository = licenseRepository;
        this.alertRepository = alertRepository;
        this.alertNotificationGateway = alertNotificationGateway;
        this.clock = clock;
    }

    public void handle() {
        LocalDate today = LocalDate.now(clock);
        licenseRepository.findAll(new LicenseSearchQuery(null, null))
                .forEach(license -> evaluateLicense(license, today));
    }

    private void evaluateLicense(EnvironmentalLicense license, LocalDate today) {
        long daysToExpiration = ChronoUnit.DAYS.between(today, license.getExpirationDate());
        if (daysToExpiration < 0) {
            createAlertIfMissing(license, "Licença vencida", buildExpiredMessage(license), "expired");
            return;
        }

        ALERT_WINDOWS.stream()
                .sorted()
                .filter(window -> daysToExpiration <= window)
                .findFirst()
                .ifPresent(window -> createAlertIfMissing(
                        license,
                        "Licença vence em " + window + " dias",
                        buildWindowMessage(license, window),
                        "due-in-" + window
                ));
    }

    private void createAlertIfMissing(EnvironmentalLicense license, String title, String message, String stageKey) {
        String dedupKey = "license:" + license.getId() + ":" + stageKey;
        if (alertRepository.existsByDedupKey(dedupKey)) {
            return;
        }

        Alert alert = Alert.builder()
                .licenseId(license.getId())
                .clientId(license.getClientId())
                .unitId(license.getUnitId())
                .type(AlertType.LICENSE_EXPIRATION)
                .title(title)
                .message(message)
                .dedupKey(dedupKey)
                .createdAt(LocalDateTime.now(clock))
                .build();

        alertRepository.save(alert);
        alertNotificationGateway.notify(alert);
    }

    private String buildExpiredMessage(EnvironmentalLicense license) {
        return "A licença " + license.getName() + " venceu em " + license.getExpirationDate().format(DATE_FORMATTER) + ".";
    }

    private String buildWindowMessage(EnvironmentalLicense license, int window) {
        return "A licença " + license.getName() + " vence em " + license.getExpirationDate().format(DATE_FORMATTER)
                + " (janela de " + window + " dias).";
    }
}
