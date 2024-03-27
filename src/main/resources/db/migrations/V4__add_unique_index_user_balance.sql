CREATE UNIQUE INDEX IF NOT EXISTS unique_user_balance_index
ON user_balance (payer, payee, group_id);