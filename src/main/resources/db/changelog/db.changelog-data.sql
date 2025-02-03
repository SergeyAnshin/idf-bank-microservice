--changeset Sergey:insert-into-expense-category
INSERT INTO expense_category(val) VALUES
    ('product'),
    ('service');

--changeset Sergey:insert-into-currency
INSERT INTO currency(alpha_code) VALUES
    ('USD'),
    ('RUB'),
    ('KZT');

--changeset Sergey:insert-into-bank-account
INSERT INTO bank_account(number,currency_id) VALUES
    ('1',1),
    ('2',1);

--changeset Sergey:insert-into-expense-limit
INSERT INTO expense_limit(val,expense_category_id,bank_account_id,created_at) VALUES
    (1000,1,1,'2022-01-01 20:08:54+06'),
    (2000,1,1,'2022-01-10 20:08:54+06'),
    (1000,1,2,'2022-02-01 20:08:54+06'),
    (400,1,2,'2022-02-10 20:08:54+06');

--changeset Sergey:insert-into-currency-pair
INSERT INTO currency_pair(buy_currency_id,sell_currency_id) VALUES
    (1,3),
    (1,2);

--changeset Sergey:insert-into-exchange-rate-source
INSERT INTO exchange_rate_source(url_template,api_key,name) VALUES
    ('https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency=%s&to_currency=%s&apikey=%s','ES3PFCQHHI4ZJA3C','ALPHA_VANTAGE'),
    ('https://api.twelvedata.com/exchange_rate?symbol=%s/%s&apikey=%s','89f105f88ede4ee2b2db9a005bb8074b','TWELVE_DATA');

--changeset Sergey:insert-into-exchange-rate-source-has-currency-pair
INSERT INTO currency_pair_has_exchange_rate_source(rate_source_id,currency_pair_id) VALUES
    (1,1),
    (1,2),
    (2,1),
    (2,2);

--changeset Sergey:insert-into-exchange-rate
INSERT INTO exchange_rate(buy_currency_id,sell_currency_id, buy_rate) VALUES
    (1,3,0.0019),
    (1,2,0.0102);

--changeset Sergey:insert-into-debit-transaction
INSERT INTO debit_transaction(client_account_id,counterparty_account_id,account_currency_id,amount,converted_amount,expense_category_id,date_time,limit_exceeded) VALUES
    (1,2,1,500,500,1,'2022-01-02 20:08:54+06',false),
    (1,2,1,600,600,1,'2022-01-03 20:08:54+06',true),
    (1,2,1,100,100,1,'2022-01-11 20:08:54+06',false),
    (1,2,1,700,700,1,'2022-01-12 20:08:54+06',false),
    (1,2,1,100,100,1,'2022-01-13 20:08:54+06',false),
    (1,2,1,100,100,1,'2022-01-13 20:08:54+06',true);

INSERT INTO debit_transaction(client_account_id,counterparty_account_id,account_currency_id,amount,converted_amount,expense_category_id,date_time,limit_exceeded) VALUES
    (2,1,1,500,500,1,'2022-01-02 20:08:54+06',false),
    (2,1,1,100,100,1,'2022-01-03 20:08:54+06',false),
    (2,1,1,100,100,1,'2022-02-11 20:08:54+06',true),
    (2,1,1,100,100,1,'2022-02-12 20:08:54+06',true);