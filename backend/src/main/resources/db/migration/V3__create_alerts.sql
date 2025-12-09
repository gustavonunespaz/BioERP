CREATE TABLE alerts (
    id UUID PRIMARY KEY,
    client_id UUID NOT NULL,
    unit_id UUID NOT NULL,
    license_id UUID NOT NULL,
    type VARCHAR(64) NOT NULL,
    title VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    dedup_key VARCHAR(255) NOT NULL UNIQUE,
    is_read BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_alert_client FOREIGN KEY (client_id) REFERENCES clients(id),
    CONSTRAINT fk_alert_unit FOREIGN KEY (unit_id) REFERENCES units(id),
    CONSTRAINT fk_alert_license FOREIGN KEY (license_id) REFERENCES environmental_licenses(id)
);
