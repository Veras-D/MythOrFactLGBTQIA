ALTER TABLE users ADD COLUMN confirmation_token VARCHAR(255);
ALTER TABLE users ADD COLUMN token_creation_date DATETIME;