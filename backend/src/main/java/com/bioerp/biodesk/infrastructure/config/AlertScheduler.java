package com.bioerp.biodesk.infrastructure.config;

import com.bioerp.biodesk.core.application.GenerateAlertsUseCase;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AlertScheduler {

    private final GenerateAlertsUseCase generateAlertsUseCase;

    public AlertScheduler(GenerateAlertsUseCase generateAlertsUseCase) {
        this.generateAlertsUseCase = generateAlertsUseCase;
    }

    @Scheduled(cron = "0 0 6 * * *")
    public void runDailyAlertGeneration() {
        generateAlertsUseCase.handle();
    }
}
