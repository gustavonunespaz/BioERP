package com.bioerp.biodesk.core.ports;

public interface AuditLogger {
    void log(String action, String detail);
}
