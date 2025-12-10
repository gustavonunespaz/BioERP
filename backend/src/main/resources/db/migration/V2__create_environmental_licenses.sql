CREATE TABLE environmental_licenses (
    id UUID PRIMARY KEY,
    client_id UUID REFERENCES clients(id) ON DELETE CASCADE,
    unit_id UUID NOT NULL REFERENCES units(id) ON DELETE CASCADE,
    type VARCHAR(128) NOT NULL,
    name VARCHAR(255),
    agency VARCHAR(255) NOT NULL,
    issuing_authority VARCHAR(255),
    process_number VARCHAR(128) NOT NULL,
    license_number VARCHAR(128) NOT NULL,
    issue_date DATE NOT NULL,
    expiration_date DATE NOT NULL,
    status VARCHAR(64) NOT NULL,
    responsible_user_id UUID REFERENCES users(id),
    tags TEXT ARRAY,
    notes TEXT,
    renewal_lead_time_days INTEGER DEFAULT 0,
    renewal_requested BOOLEAN DEFAULT FALSE,
    renewal_requested_at DATE,
    conditions TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_environmental_licenses_unit_id ON environmental_licenses(unit_id);
CREATE INDEX idx_environmental_licenses_expiration_date ON environmental_licenses(expiration_date);

CREATE TABLE license_documents (
    id UUID PRIMARY KEY,
    license_id UUID NOT NULL REFERENCES environmental_licenses(id) ON DELETE CASCADE,
    doc_type VARCHAR(128) NOT NULL,
    description TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_license_document_type UNIQUE (license_id, doc_type)
);

CREATE TABLE license_document_versions (
    id UUID PRIMARY KEY,
    document_id UUID NOT NULL REFERENCES license_documents(id) ON DELETE CASCADE,
    version_number INTEGER NOT NULL,
    file_url TEXT,
    file_name VARCHAR(255) NOT NULL,
    file_hash VARCHAR(255),
    uploaded_by UUID REFERENCES users(id),
    uploaded_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    review_notes TEXT,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    content_type VARCHAR(128),
    file_data BYTEA,
    CONSTRAINT uq_document_version UNIQUE (document_id, version_number)
);

CREATE TABLE license_conditions (
    id UUID PRIMARY KEY,
    license_id UUID NOT NULL REFERENCES environmental_licenses(id) ON DELETE CASCADE,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    due_date DATE,
    status VARCHAR(64) NOT NULL,
    responsible_user_id UUID REFERENCES users(id),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_license_conditions_due_date ON license_conditions(due_date);

