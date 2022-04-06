create schema hb;

create table hb.users
(
    user_id    uuid        not null
        primary key,
    email      varchar(50) not null
        unique,
    password   varchar(60) not null,
    first_name varchar(50)
);

create table hb.users_categories
(
    user_id       uuid                  not null
        references hb.users,
    category_name varchar(30)           not null,
    is_default    boolean default false not null,
    primary key (user_id, category_name)
);

create table hb.transactions
(
    transaction_id uuid                                not null
        primary key,
    sum            numeric                             not null,
    create_time    timestamp default CURRENT_TIMESTAMP not null,
    user_id        uuid                                not null
        references hb.users,
    category_name  varchar(50),
    description    text
);

