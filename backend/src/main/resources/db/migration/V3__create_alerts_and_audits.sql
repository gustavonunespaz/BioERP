CREATE TABLE alerts (
    id UUID PRIMARY KEY,
    client_id UUID REFERENCES clients(id) ON DELETE SET NULL,
    unit_id UUID REFERENCES units(id) ON DELETE SET NULL,
    license_id UUID NOT NULL REFERENCES environmental_licenses(id) ON DELETE CASCADE,
    condition_id UUID REFERENCES license_conditions(id) ON DELETE SET NULL,
    alert_type VARCHAR(128) NOT NULL,
    severity VARCHAR(32) NOT NULL,
    trigger_date DATE NOT NULL,
    due_date DATE,
    message TEXT,
    channel VARCHAR(64),
    dedup_key VARCHAR(255) NOT NULL UNIQUE,
    is_read BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_alerts_due_date ON alerts(due_date);
CREATE INDEX idx_alerts_severity_read ON alerts(severity, is_read);

CREATE TABLE audit_logs (
    id UUID PRIMARY KEY,
    actor_user_id UUID NOT NULL REFERENCES users(id),
    entity_type VARCHAR(128) NOT NULL,
    entity_id UUID NOT NULL,
    action VARCHAR(64) NOT NULL,
    summary TEXT,
    diff_json JSONB,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_audit_logs_entity ON audit_logs(entity_type, entity_id);

