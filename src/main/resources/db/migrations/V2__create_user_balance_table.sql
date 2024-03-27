CREATE TABLE IF NOT EXISTS user_balance (
    user_balance_id SERIAL PRIMARY KEY,
    payer INT,
    payee INT,
    amount FLOAT,
    group_id INT,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (group_id) REFERENCES groups(group_id),
    FOREIGN KEY (payer) REFERENCES users(user_id),
    FOREIGN KEY (payee) REFERENCES users(user_id)
);