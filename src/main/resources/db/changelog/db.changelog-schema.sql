--changeset Sergey:create-table-bank-account
CREATE TABLE bank_account (
    bank_account_id BIGINT AUTO_INCREMENT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    number INT NOT NULL,
    CONSTRAINT pk_bank_account_id PRIMARY KEY (bank_account_id)
);

--changeset Sergey:create-table-expense-category
CREATE TABLE expense_category (
    expense_category_id BIGINT AUTO_INCREMENT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL ,
    name VARCHAR(10) NOT NULL,
    CONSTRAINT pk_expense_category_id PRIMARY KEY (expense_category_id)
);

--changeset Sergey:create-table-currency
CREATE TABLE currency (
    currency_id BIGINT AUTO_INCREMENT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    alpha_code VARCHAR(3) NOT NULL,
    CONSTRAINT pk_currency_id PRIMARY KEY (currency_id)
);

--changeset Sergey:create-table-expense-limit
CREATE TABLE expense_limit (
    expense_limit_id BIGINT AUTO_INCREMENT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    "value" DECIMAL(15,2) NOT NULL DEFAULT 1000,
    expense_category_id BIGINT NOT NULL,
    currency_id BIGINT NOT NULL,
    bank_account_id BIGINT NOT NULL,
    CONSTRAINT pk_expense_limit_id PRIMARY KEY (expense_limit_id)
);

--changeset Sergey:create-table-currency-balance
CREATE TABLE currency_balance (
    currency_balance_id BIGINT AUTO_INCREMENT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    "value" DECIMAL(15,2) NOT NULL,
    currency_id BIGINT NOT NULL,
    bank_account_id BIGINT NOT NULL,
    CONSTRAINT pk_currency_balance_id PRIMARY KEY (currency_balance_id)
);

--changeset Sergey:create-table-debit-transaction
CREATE TABLE debit_transaction (
    debit_transaction_id BIGINT AUTO_INCREMENT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    client_account_id BIGINT NOT NULL,
    counterparty_account_id BIGINT NOT NULL,
    account_currency_id BIGINT NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    expense_category_id BIGINT NOT NULL,
    date_time TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT pk_debit_transaction_id PRIMARY KEY (debit_transaction_id)
);