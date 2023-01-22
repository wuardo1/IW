INSERT INTO users (user_id, date_of_birth, first_name, important, last_name, mail, occupation, password, phone)
VALUES ('d9819c24-7606-41c2-97f8-1cf576f58a78', null, null, false, null, 'x@y.de', null,
        '$2a$10$8QwamsZIVgpb.FuL66iOveqC5Bt7ekV59HSAVYiAc/kBKKgpWLC2i', null),
       ('d9819c24-7606-41c2-97f8-1cf523f58a78', null, null, false, null, 'a@y.de', null,
        '$2a$10$8QwamsZIVgpb.FuL66iOveqC5Bt7ekV59HSAVYiAc/kBKKgpWLC2i', null);

INSERT INTO user_groups (user_id, user_group)
VALUES ('d9819c24-7606-41c2-97f8-1cf576f58a78', 'ROLE_USER'),
       ('d9819c24-7606-41c2-97f8-1cf523f58a78', 'ROLE_USER');

INSERT INTO bank_accounts (bank_account_id, iban, balance, credit_line, user_id)
VALUES ('8ef7289c-9996-41a6-860d-4e3f941c7dd6', 'ES1220389717824292519288', 50, 0,
        'd9819c24-7606-41c2-97f8-1cf576f58a78'),
       ('d9819c24-1206-41c2-97f8-1cf576f58a78', 'ES7131904395848711939975', 10, 0,
        'd9819c24-7606-41c2-97f8-1cf523f58a78');


