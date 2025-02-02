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
INSERT INTO expense_limit(expense_category_id,currency_id,bank_account_id) VALUES
    (1,1,1),
    (2,1,1),
    (1,1,2),
    (2,1,2);

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