-- Create Users Table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL
);

-- Create Accounts Table
CREATE TABLE accounts (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    balance NUMERIC(10, 2) DEFAULT 0,
    winnings NUMERIC(10, 2) DEFAULT 0,
    losses NUMERIC(10, 2) DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create Bets Table
CREATE TABLE bets (
    id SERIAL PRIMARY KEY,
    account_id INTEGER NOT NULL,
    sport VARCHAR(50) NOT NULL,
    team VARCHAR(50) NOT NULL,
    amount NUMERIC(10, 2) NOT NULL,
    type VARCHAR(10) NOT NULL,
    odds NUMERIC(10, 2) NOT NULL,
    result BOOLEAN,
    FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE
);
