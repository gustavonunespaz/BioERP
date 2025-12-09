package com.bioerp.biodesk.infrastructure.config;

import com.bioerp.biodesk.core.domain.service.CnpjValidator;
import com.bioerp.biodesk.core.domain.service.LicenseStatusEvaluator;
import java.time.Clock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public CnpjValidator cnpjValidator() {
        return new CnpjValidator();
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public LicenseStatusEvaluator licenseStatusEvaluator(Clock clock) {
        return new LicenseStatusEvaluator(clock);
    }
}
