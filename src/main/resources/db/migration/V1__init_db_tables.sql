create table if not exists address
(
    id              bigserial primary key,
    display_address text                                                           not null,
    post_code       varchar(25),
    city            varchar(100),
    street          varchar(256),
    created_at      timestamp without time zone default (now() at time zone 'utc') not null
);

CREATE TABLE users
(
    id           bigserial PRIMARY KEY NOT NULL,
    first_name   varchar(100),
    last_name    varchar(100),
    email        varchar(100)          NOT NULL UNIQUE,
    phone_number varchar(100)          NOT NULL UNIQUE,
    password     varchar(256)          NOT NULL,
    role         varchar(25),
    address_id   int                   NOT NULL
);


create table if not exists orders
(
    id         bigserial primary key,
    user_id    bigint                                                         NOT NULL,
    status     varchar(25)                                                    NOT NULL,
    created_at timestamp without time zone default (now() at time zone 'utc') NOT NULL
);

create table if not exists category
(
    id            bigserial primary key,
    name          varchar(256) not null unique,
    average_price double precision,
    enabled       boolean      NOT NULL default true
);

create table if not exists product
(
    id          bigserial primary key,
    category_id bigint           not null,
    title       varchar(256)     NOT NULL,
    description text,
    price       double precision NOT NULL
);

create table if not exists order_product
(
    order_id   bigint,
    product_id bigint,
    PRIMARY KEY (order_id, product_id)
);

-- create table to test drop table
CREATE TABLE to_delete(id serial);

-- example on how to delete existing table
DROP TABLE to_delete;

ALTER TABLE users
    ADD CONSTRAINT fk_users_address
        FOREIGN KEY (address_id)
            REFERENCES address (id) ON DELETE CASCADE ON UPDATE CASCADE;


alter table order_product
    add constraint order_product_orders_id_fk
        foreign key (order_id) references orders
            ON DELETE RESTRICT ON UPDATE CASCADE;

alter table order_product
    add constraint order_product_product_id_fk
        foreign key (product_id) references product
            ON DELETE RESTRICT ON UPDATE CASCADE;

alter table product
    add constraint fk_category_product
        foreign key (category_id) references category (id)
            ON DELETE CASCADE ON UPDATE CASCADE;

