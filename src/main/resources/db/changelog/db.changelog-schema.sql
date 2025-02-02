--changeset Sergey:create-table-bank-account
CREATE TABLE bank_account (
    bank_account_id SERIAL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    number INT NOT NULL,
    CONSTRAINT pk_bank_account_id PRIMARY KEY (bank_account_id)
);

--changeset Sergey:create-table-expense-category
CREATE TABLE expense_category (
    expense_category_id SERIAL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL ,
    val VARCHAR(10) NOT NULL,
    CONSTRAINT pk_expense_category_id PRIMARY KEY (expense_category_id)
);

--changeset Sergey:create-table-currency
CREATE TABLE currency (
    currency_id SERIAL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    alpha_code VARCHAR(3) NOT NULL,
    CONSTRAINT pk_currency_id PRIMARY KEY (currency_id)
);

--changeset Sergey:create-table-expense-limit
CREATE TABLE expense_limit (
    expense_limit_id SERIAL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    val DECIMAL(15,6) NOT NULL DEFAULT 1000,
    expense_category_id BIGINT NOT NULL,
    bank_account_id BIGINT NOT NULL,
    CONSTRAINT pk_expense_limit_id PRIMARY KEY (expense_limit_id)
);

--changeset Sergey:create-table-debit-transaction
CREATE TABLE debit_transaction (
    debit_transaction_id SERIAL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    client_account_id BIGINT NOT NULL,
    counterparty_account_id BIGINT NOT NULL,
    account_currency_id BIGINT NOT NULL,
    amount DECIMAL(15,6) NOT NULL,
    converted_amount DECIMAL(15,6) NOT NULL,
    expense_category_id BIGINT NOT NULL,
    date_time TIMESTAMP WITH TIME ZONE NOT NULL,
    limit_exceeded BOOLEAN NOT NULL,
    CONSTRAINT pk_debit_transaction_id PRIMARY KEY (debit_transaction_id)
);

--changeset Sergey:create-table-currency-pair
CREATE TABLE currency_pair (
    currency_pair_id SERIAL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    buy_currency_id BIGINT NOT NULL,
    sell_currency_id BIGINT NOT NULL,
    CONSTRAINT pk_currency_pair_id PRIMARY KEY (currency_pair_id)
);

--changeset Sergey:create-table-exchange-rate-source
CREATE TABLE exchange_rate_source (
    rate_source_id SERIAL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    name VARCHAR NOT NULL,
    url_template VARCHAR NOT NULL,
    api_key VARCHAR,
    CONSTRAINT pk_rate_source_id PRIMARY KEY (rate_source_id)
);

--changeset Sergey:create-table-currency-pair-has-exchange-rate-source
CREATE TABLE currency_pair_has_exchange_rate_source (
    pair_has_source_id SERIAL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    rate_source_id BIGINT NOT NULL,
    currency_pair_id BIGINT NOT NULL,
    CONSTRAINT pk_currency_pair_has_exchange_rate_source PRIMARY KEY (pair_has_source_id)
);

--changeset Sergey:create-table-exchange-rate
CREATE TABLE exchange_rate (
    exchange_rate_id SERIAL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    buy_currency_id BIGINT NOT NULL,
    sell_currency_id BIGINT NOT NULL,
    buy_rate DECIMAL(15,6) NOT NULL,
    CONSTRAINT pk_exchange_rate_id PRIMARY KEY (exchange_rate_id)
);

--changeset Sergey:add-column-bank-account
ALTER TABLE bank_account ADD COLUMN currency_id BIGINT NOT NULL;

--changeset Sergey:add-foreign-key-bank-account-to-currency
ALTER TABLE bank_account ADD CONSTRAINT fk_bank_account_currency FOREIGN KEY (currency_id)
    REFERENCES currency (currency_id);

--changeset Sergey:add-foreign-key-currency-pair-to-buy-currency
ALTER TABLE currency_pair ADD CONSTRAINT fk_currency_pair_buy_currency FOREIGN KEY (buy_currency_id)
    REFERENCES currency (currency_id);

--changeset Sergey:add-foreign-key-currency-pair-to-sell-currency
ALTER TABLE currency_pair ADD CONSTRAINT fk_currency_pair_sell_currency FOREIGN KEY (sell_currency_id)
    REFERENCES currency (currency_id);

--changeset Sergey:add-foreign-key-exchange-rate-source-currency-pair
ALTER TABLE currency_pair_has_exchange_rate_source ADD CONSTRAINT fk_exchange_rate_source_has_currency_pair FOREIGN KEY (currency_pair_id)
    REFERENCES currency_pair (currency_pair_id);

--changeset Sergey:add-foreign-key-currency-pair-exchange-rate-source
ALTER TABLE currency_pair_has_exchange_rate_source ADD CONSTRAINT fk_currency_pair_has_exchange_rate_source FOREIGN KEY (rate_source_id)
    REFERENCES exchange_rate_source (rate_source_id);

--changeset Sergey:add-foreign-key-expense-limit-to-expense-category
ALTER TABLE expense_limit ADD CONSTRAINT fk_expense_limit_expense_category FOREIGN KEY (expense_category_id)
    REFERENCES expense_category (expense_category_id);

--changeset Sergey:add-foreign-key-expense-limit-to-bank-account
ALTER TABLE expense_limit ADD CONSTRAINT fk_expense_limit_bank_account FOREIGN KEY (bank_account_id)
    REFERENCES bank_account (bank_account_id);

--changeset Sergey:add-unique-currency-pair-exchange-rate-source
ALTER TABLE currency_pair ADD CONSTRAINT uk_buy_sell_currency_id
    UNIQUE (buy_currency_id,sell_currency_id);

--changeset Sergey:add-unique-currency-alpha_code
ALTER TABLE currency ADD CONSTRAINT uk_currency_alpha_code
    UNIQUE (alpha_code);

--changeset Sergey:add-unique-exchange-rate
ALTER TABLE exchange_rate ADD CONSTRAINT uk_created_at_buy_sell_currency_id
    UNIQUE (created_at,buy_currency_id,sell_currency_id);

--changeset Sergey:add-unique-exchange-rate-source
ALTER TABLE exchange_rate_source ADD CONSTRAINT uk_rate_source_name
    UNIQUE (name);

--changeset Sergey:add-unique-expense-limit
ALTER TABLE expense_limit ADD CONSTRAINT uk_created_at_val_expense_category_id_bank_account_id
    UNIQUE (created_at,val,expense_category_id,bank_account_id);
