--changeset Sergey:insert-into-expense-category
INSERT INTO expense_category(name) VALUES ('product'), ('service');

--changeset Sergey:insert-into-currency
INSERT INTO currency(alpha_code) VALUES ('USD'), ('RUB'), ('KZT');
