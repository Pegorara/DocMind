CREATE TABLE document (
                          id UUID PRIMARY KEY,
                          original_file_name VARCHAR(255) NOT NULL,
                          storage_path VARCHAR(500) NOT NULL,
                          size_in_bytes BIGINT NOT NULL,
                          created_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE job (
                     id UUID PRIMARY KEY,
                     document_id UUID NOT NULL REFERENCES document(id),
                     status VARCHAR(20) NOT NULL,
                     error_message TEXT,
                     created_at TIMESTAMPTZ NOT NULL,
                     started_at TIMESTAMPTZ,
                     finished_at TIMESTAMPTZ
);

CREATE INDEX idx_job_document_id ON job(document_id);
CREATE INDEX idx_job_status ON job(status);