-- ===================================================================
-- DML SCRIPT FOR VBank DATABASE
-- ===================================================================

-- Clear existing data
TRUNCATE TABLE transactions RESTART IDENTITY CASCADE;
TRUNCATE TABLE accounts RESTART IDENTITY CASCADE;
TRUNCATE TABLE clients RESTART IDENTITY CASCADE;

-- Insert clients
INSERT INTO clients (name, document, email, phone, address) VALUES
  ('Luiz Eduardo da Silva', '11122233344', 'luiz.e.silva@email.com', '11987654321', 'Rua Bem Legal, 123, São Paulo, SP'),
  ('Douglas Castelluber', '22233344455', 'douglas.castelluber@email.com', '11912345678', 'Avenida da Felicidade, 456, São Paulo, SP');

-- Insert accounts for clients
INSERT INTO accounts (client_id, account_number, balance) VALUES
  (1, '0001-1', 1500.75),
  (2, '0002-1', 850.25);

-- Insert transactions for accounts
INSERT INTO transactions (account_id, type, status, amount, "timestamp") VALUES
  (1, 'DEPOSIT', 'COMPLETED', 500.00, '2023-08-19 10:00:00'),
  (1, 'WITHDRAWAL', 'COMPLETED', 150.25, '2023-08-20 14:30:00'),
  (2, 'DEPOSIT', 'COMPLETED', 1000.00, '2023-08-20 09:15:00');

-- Reset sequences to avoid conflicts with future inserts
SELECT setval('clients_id_seq', (SELECT MAX(id) FROM clients));
SELECT setval('accounts_id_seq', (SELECT MAX(id) FROM accounts));
SELECT setval('transactions_id_seq', (SELECT MAX(id) FROM transactions));