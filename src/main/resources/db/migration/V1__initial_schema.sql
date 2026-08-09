CREATE TABLE roles (
                       id BIGSERIAL PRIMARY KEY, version BIGINT NOT NULL DEFAULT 0,
                       created_at TIMESTAMPTZ NOT NULL, updated_at TIMESTAMPTZ NOT NULL,
                       code varchar(50) NOT NULL UNIQUE, description varchar(255)
);
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY, version BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMPTZ NOT NULL, updated_at TIMESTAMPTZ NOT NULL,
    email VARCHAR(254) NOT NULL, password VARCHAR(255) NOT NULL, name VARCHAR(100) NOT NULL,
    role_id BIGINT NOT NULL REFERENCES roles(id), CONSTRAINT uk_users_email UNIQUE (email)
);
CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY, version BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMPTZ NOT NULL, updated_at TIMESTAMPTZ NOT NULL,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE, name VARCHAR(80) NOT NULL, color VARCHAR(7) NOT NULL,
    CONSTRAINT uk_categories_user_name UNIQUE (user_id,name), CONSTRAINT ck_categories_color CHECK (color ~ '^#[0-9A-Fa-f]{6}$')
);
CREATE TABLE transactions (
    id BIGSERIAL PRIMARY KEY, version BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMPTZ NOT NULL, updated_at TIMESTAMPTZ NOT NULL,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    category_id BIGINT NOT NULL REFERENCES categories(id), type VARCHAR(10) NOT NULL,
    amount NUMERIC(19,2) NOT NULL, transaction_date DATE NOT NULL, description VARCHAR(500),
    CONSTRAINT ck_transactions_type CHECK (type IN ('INCOME','EXPENSE')), CONSTRAINT ck_transactions_amount CHECK (amount > 0)
);
CREATE INDEX idx_transactions_user_date ON transactions(user_id,transaction_date);
CREATE TABLE budgets (
    id BIGSERIAL PRIMARY KEY, version BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMPTZ NOT NULL, updated_at TIMESTAMPTZ NOT NULL,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    category_id BIGINT NOT NULL REFERENCES categories(id), amount NUMERIC(19,2) NOT NULL,
    start_date DATE NOT NULL, end_date DATE NOT NULL,
    CONSTRAINT ck_budgets_amount CHECK (amount > 0), CONSTRAINT ck_budgets_dates CHECK (start_date <= end_date)
);
CREATE INDEX idx_budgets_user_period ON budgets(user_id,start_date,end_date);
