package com.bioerp.biodesk.infrastructure.logging;

import com.bioerp.biodesk.core.ports.AuditLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingAuditLogger implements AuditLogger {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAuditLogger.class);

    @Override
    public void log(String action, String detail) {
        LOGGER.info("[AUDIT] {} - {}", action, detail);
    }
}
