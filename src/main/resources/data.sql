-- ===================================================================
-- DML SCRIPT FOR VBank DATABASE
-- ===================================================================

-- Insert clients
INSERT INTO clients (id, name, document, email, phone, address) VALUES
                                                                    (1, 'Luiz Eduardo da Silva', '11122233344', 'luiz.e.silva@email.com', '11987654321', 'Rua Bem Legal, 123, São Paulo, SP'),
                                                                    (2, 'Douglas Castelluber', '22233344455', 'douglas.castelluber@email.com', '1191234-5678', 'Avenida da Felicidade, 456, São Paulo, SP');

-- Insert accounts for clients
-- Note: Assuming bigserial starts IDs from 1
INSERT INTO accounts (id, client_id, account_number, balance) VALUES
                                                                  (1, 1, '0001-1', 1500.75), -- Luiz's account
                                                                  (2, 2, '0002-1', 850.25);  -- Douglas's account

-- Insert transactions for accounts
INSERT INTO transactions (id, account_id, type, status, amount, "timestamp") VALUES
                                                                                 (1, 1, 'DEPOSIT', 'COMPLETED', 500.00, '2025-08-19 10:00:00'),
                                                                                 (2, 1, 'WITHDRAWAL', 'COMPLETED', 150.25, '2025-08-20 14:30:00'),
                                                                                 (3, 2, 'DEPOSIT', 'COMPLETED', 1000.00, '2025-08-20 09:15:00');

-- Since bigserial is used, the sequence for the ID needs to be updated to avoid conflicts if the application tries to insert new records.
SELECT setval('clients_id_seq', (SELECT MAX(id) FROM clients));
SELECT setval('accounts_id_seq', (SELECT MAX(id) FROM accounts));
SELECT setval('transactions_id_seq', (SELECT MAX(id) FROM transactions));