package com.bioerp.biodesk.infrastructure.config;

import com.bioerp.biodesk.core.domain.service.CnpjValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public CnpjValidator cnpjValidator() {
        return new CnpjValidator();
    }
}
