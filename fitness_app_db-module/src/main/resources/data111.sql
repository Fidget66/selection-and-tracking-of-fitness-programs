DROP TABLE IF EXISTS review cascade;
DROP TABLE IF EXISTS roles cascade;
DROP TABLE IF EXISTS category_of_fitness_program cascade;
DROP TABLE IF EXISTS fitness_program cascade;
DROP TABLE IF EXISTS exercise_schedule cascade;
DROP TABLE IF EXISTS bookmark cascade;
DROP TABLE IF EXISTS active_program cascade;
DROP TABLE IF EXISTS users cascade;
DROP TABLE IF EXISTS users_security cascade;

CREATE TABLE IF NOT EXISTS users(
    id bigserial NOT NULL,
    first_name varchar(255),
    last_name varchar(255),
    date_of_birth date,
    sex varchar(1),
    weight smallint,
    email varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS category_of_fitness_program(
    id bigserial NOT NULL,
    short_name varchar(255),
    description varchar,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS roles(
    id bigserial NOT NULL,
    role_name varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS fitness_program(
    id bigserial NOT NULL,
    short_name varchar(255),
    duration smallint,
    age_restriction smallint,
    weight_restriction smallint,
    sex_restriction varchar(1),
    exercise_per_week smallint,
    description varchar,
    category_id bigint,
    PRIMARY KEY (id),
    FOREIGN KEY (category_id) REFERENCES category_of_fitness_program(id)
);

CREATE TABLE IF NOT EXISTS review(
    id bigserial NOT NULL,
    author_id bigint,
    text varchar,
    fitness_program_id bigint,
    PRIMARY KEY (id),
    FOREIGN KEY (fitness_program_id) REFERENCES fitness_program(id)
);

CREATE TABLE IF NOT EXISTS bookmark(
    id bigserial NOT NULL,
    fitness_program_id bigint,
    users_id bigint,
    PRIMARY KEY (id),
    FOREIGN KEY (fitness_program_id) REFERENCES fitness_program(id),
    FOREIGN KEY (users_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS active_program(
    id bigserial NOT NULL,
    is_complited boolean,
    fitness_program_id bigint,
    users_id bigint,
    PRIMARY KEY (id),
    FOREIGN KEY (fitness_program_id) REFERENCES fitness_program(id),
    FOREIGN KEY (users_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS active_program(
    id bigserial NOT NULL,
    exercise_date date,
    is_complited boolean,
    active_program_id bigint,
    PRIMARY KEY (id),
    FOREIGN KEY (active_program_id) REFERENCES active_program(id)
);

CREATE TABLE IF NOT EXISTS users_security(
    id bigserial NOT NULL,
    is_account_non_locked boolean,
    login varchar,
    password varchar,
    user_id bigint,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS customer_shop(
    users_security_id bigint NOT NULL,
    roles_id bigint NOT NULL,
    PRIMARY KEY (users_security_id, roles_id),
    FOREIGN KEY (users_security_id) REFERENCES users_security(id),
    FOREIGN KEY (roles_id) REFERENCES roles(id)
);







ALTER TABLE cities ADD COLUMN IF NOT EXISTS shop_id bigint REFERENCES shop(id);



INSERT INTO cities(city) values('BREST');
INSERT INTO cities(city) values('GRODNO');
INSERT INTO cities(city) values('VITEBSK');
INSERT INTO cities(city) values('MINSK');

INSERT INTO shop(name_shop, foundation_date, is_always_open, is_only_online, phone_number, email, postcode, city_city_id)
values ('Mile', '2010-01-20', false, false, 11223344, 'Mile@mail.ru', 224000, 1);
INSERT INTO shop(name_shop, foundation_date, is_always_open, is_only_online, phone_number, email, postcode, city_city_id)
values ('Materik', '2005-05-18', true, false, 99887766, 'Materik@gmail.com', 230145, 2);
INSERT INTO shop(name_shop, foundation_date, is_always_open, is_only_online, phone_number, email, postcode, city_city_id)
values ('21vek', '2018-09-09', true, true, 98765432, '21vek@gmail.com', 123987, 3);
INSERT INTO shop(name_shop, foundation_date, is_always_open, is_only_online, phone_number, email, postcode, city_city_id)
values ('OMA', '2016-05-12', true, false, 852963, 'OMA@gmail.com', 654456, 4);

INSERT INTO customer(first_name, last_name, birthday, sex, is_vip, phone_number, account_number)
values ('Oleg', 'Olegov', '1970-12-10', 'm', true, 456123, 1230045609);
INSERT INTO customer(first_name, last_name, birthday, sex, is_vip, phone_number, account_number)
values ('Vova', 'Vovchik', '1984-02-02', 'm', false, 999333, 256007890);
INSERT INTO customer(first_name, last_name, birthday, sex, is_vip, phone_number, account_number)
values ('Fedor', 'Oprichnik', '1930-06-29', 'm', true, 00000, 100001);
INSERT INTO customer(first_name, last_name, birthday, sex, is_vip, phone_number, account_number)
values ('Olga', 'Petrova', '1981-12-12', 'w', false, 4569784, 1235874);

INSERT INTO purchase_order(buy_time, sum, is_confirmed, is_delivery, name_customer, phone_number_customer, name_shop, customer_id)
values (clock_timestamp(), 111.58, true, false, 'Oleg', 456123, 'Mile',1);
INSERT INTO purchase_order(buy_time, sum, is_confirmed, is_delivery, name_customer, phone_number_customer, name_shop, customer_id)
values (clock_timestamp(), 547.33, true, true, 'Oleg', 456123, 'Materik',1);
INSERT INTO purchase_order(buy_time, sum, is_confirmed, is_delivery, name_customer, phone_number_customer, name_shop, customer_id)
values (clock_timestamp(), 1000.01, true, true, 'Vova', 456123, '21vek',2);
INSERT INTO purchase_order(buy_time, sum, is_confirmed, is_delivery, name_customer, phone_number_customer, name_shop, customer_id)
values (clock_timestamp(), 233256.09, true, true, 'Fedor', 00000, '21vek',3);
INSERT INTO purchase_order(buy_time, sum, is_confirmed, is_delivery, name_customer, phone_number_customer, name_shop, customer_id)
values (clock_timestamp(), 999.99, true, false, 'Olga', 4569784, 'Materik',4);

INSERT INTO good(price, production_date, name, article_product, is_available, color, quantity, order_id)
values (12.58, '2020-12-12', 'concrete', 784512, true, 'black', 124, 1);
INSERT INTO good(price, production_date, name, article_product, is_available, color, quantity, order_id)
values (150.55, '2021-03-29', 'paint', 456123, true, 'white', 15, 1);
INSERT INTO good(price, production_date, name, article_product, is_available, color, quantity, order_id)
values (45.89, '2021-07-01', 'phone', 7563125, true, 'gray', 5, 2);
INSERT INTO good(price, production_date, name, article_product, is_available, color, quantity, order_id)
values (1.12, '2020-10-01', 'salt', 75210, true, 'white', 598, 2);
INSERT INTO good(price, production_date, name, article_product, is_available, color, quantity, order_id)
values (1.12, '2021-02-01', 'sugar', 213169, true, 'white', 952, 3);
INSERT INTO good(price, production_date, name, article_product, is_available, color, quantity, order_id)
values (5.02, '2021-06-06', 'vinegar', 7845120, true, 'transparent', 23, 4);
INSERT INTO good(price, production_date, name, article_product, is_available, color, quantity, order_id)
values (1.12, '1950-01-01', 'home wine', 3654, true, 'red', 2, 4);
INSERT INTO good(price, production_date, name, article_product, is_available, color, quantity, order_id)
values (568.12, '2021-01-09', 'lipstick', 215487, true, 'red', 1, 5);

INSERT INTO customer_shop(customer_id, shop_id) values(1,1);
INSERT INTO customer_shop(customer_id, shop_id) values(1,2);
INSERT INTO customer_shop(customer_id, shop_id) values(2,3);
INSERT INTO customer_shop(customer_id, shop_id) values(2,4);
INSERT INTO customer_shop(customer_id, shop_id) values(3,1);
INSERT INTO customer_shop(customer_id, shop_id) values(3,3);
INSERT INTO customer_shop(customer_id, shop_id) values(4,2);
INSERT INTO customer_shop(customer_id, shop_id) values(4,4);