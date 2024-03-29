INSERT INTO users (user_id, date_of_birth, first_name, important, last_name, mail, occupation, password, phone) VALUES
    ('d9819c24-7606-41c2-97f8-1cf576f58a78',null,null,false,null,'x@y.de',null,'$2a$10$8QwamsZIVgpb.FuL66iOveqC5Bt7ekV59HSAVYiAc/kBKKgpWLC2i',null),
    ('d9819c24-7606-41c2-97f8-1cf523f58a78',null,null,false,null,'y@y.de',null,'$2a$10$8QwamsZIVgpb.FuL66iOveqC5Bt7ekV59HSAVYiAc/kBKKgpWLC2i',null),
    ('9c556385-b147-4f44-9db8-086518556652',null,null,false,null,'a@y.de',null,'$2a$10$8QwamsZIVgpb.FuL66iOveqC5Bt7ekV59HSAVYiAc/kBKKgpWLC2i',null);

INSERT INTO user_groups (user_id, user_group) VALUES
    ('d9819c24-7606-41c2-97f8-1cf576f58a78','ROLE_USER'),
    ('d9819c24-7606-41c2-97f8-1cf523f58a78','ROLE_USER'),
    ('9c556385-b147-4f44-9db8-086518556652','ROLE_ADMIN');

INSERT INTO bank_accounts (bank_account_id, iban, balance, credit_line, user_id) VALUES
    ('8ef7289c-9996-41a6-860d-4e3f941c7dd6','ES1220389717824292519288',50,0,'d9819c24-7606-41c2-97f8-1cf576f58a78'),
    ('d9819c24-1206-41c2-97f8-1cf576f58a78','ES7131904395848711939975',10,0,'d9819c24-7606-41c2-97f8-1cf523f58a78');

INSERT INTO transactions (TRANSACTION_ID, AMOUNT, DATE_EXECUTED, DESTINATION_IBAN, SOURCE_IBAN, BANK_ACCOUNT_ID)
VALUES ('d9819c24-1206-41c2-97f8-1cf576f58a71', 1, 12, 'ES7131904395848711939975', 'ES7131904395848711939975','8ef7289c-9996-41a6-860d-4e3f941c7dd6');

INSERT INTO credit_cards (CREDIT_CARD_ID, ACTIVE, CARD_LIMIT, CARD_NUMBER, CCV, CURRENT_DEBT, ISSUE_DATE, VALIDITY_DATE, BANK_ACCOUNT_ID)
VALUES ('c0326266-8ac9-48f3-91a7-32ebefb36483', true, 50, 4567898765434567, 234, 0, 1, 10000000000, '8ef7289c-9996-41a6-860d-4e3f941c7dd6');
