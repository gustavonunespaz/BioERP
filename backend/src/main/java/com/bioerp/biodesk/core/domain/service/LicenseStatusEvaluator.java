package com.bioerp.biodesk.core.domain.service;

import com.bioerp.biodesk.core.domain.model.EnvironmentalLicense;
import com.bioerp.biodesk.core.domain.model.LicenseLifecycleStatus;
import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LicenseStatusEvaluator {

    private final Clock clock;

    public LicenseStatusEvaluator(Clock clock) {
        this.clock = clock;
    }

    public LicenseLifecycleStatus evaluate(EnvironmentalLicense license) {
        LocalDate today = LocalDate.now(clock);
        if (license.getExpirationDate().isBefore(today)) {
            return LicenseLifecycleStatus.VENCIDA;
        }

        long daysToExpiration = ChronoUnit.DAYS.between(today, license.getExpirationDate());
        boolean insideRenewalWindow = daysToExpiration < license.getRenewalLeadTimeDays();

        if (!license.isRenewalRequested() && insideRenewalWindow) {
            return LicenseLifecycleStatus.RENOVACAO_ATRASADA;
        }

        if (license.isRenewalRequested() && daysToExpiration >= 0) {
            return LicenseLifecycleStatus.EM_RENOVACAO;
        }

        return LicenseLifecycleStatus.VIGENTE;
    }
}
