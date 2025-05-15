CREATE TABLE payments (
    id CHAR(36) PRIMARY KEY,
    user_id bigint NOT NULL,
    subscription_id CHAR(36) NULL,
    value DECIMAL(10,2) NOT NULL,
    method_payment VARCHAR(50),
    status VARCHAR(45) NOT NULL,
    created_at DATETIME NOT NULL,
    paid_market_id VARCHAR(255) UNIQUE
);