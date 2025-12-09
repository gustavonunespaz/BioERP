CREATE TABLE environmental_licenses (
    id UUID PRIMARY KEY,
    client_id UUID NOT NULL REFERENCES clients(id) ON DELETE CASCADE,
    unit_id UUID NOT NULL REFERENCES units(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    issuing_authority VARCHAR(255) NOT NULL,
    issue_date DATE NOT NULL,
    expiration_date DATE NOT NULL,
    renewal_lead_time_days INTEGER NOT NULL,
    renewal_requested BOOLEAN NOT NULL,
    renewal_requested_at DATE,
    conditions TEXT
);
