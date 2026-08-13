INSERT INTO roles (version, created_at, updated_at, code, description)
VALUES
    (0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'USER', 'Standard application user'),
    (0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ADMIN', 'Application administrator')
ON CONFLICT (code) DO NOTHING;
