create schema hb;
CREATE EXTENSION IF NOT EXISTS  pgcrypto;
create table hb.users
(
    user_email varchar(50) not null
        primary key,
    password   varchar(60) not null,
    first_name varchar(50) not null
);

create table hb.users_categories
(
    user_email    varchar(50)                       not null
        references hb.users (user_email),
    category_id   uuid    default public.gen_random_uuid() not null,
    category_name varchar(30)                       not null,
    is_default    boolean default false             not null,
    primary key (user_email, category_id),
    unique (user_email, category_name)
);

create table hb.transactions
(
    transaction_id uuid      default  public.gen_random_uuid() not null
        primary key,
    sum            decimal                             not null,
    create_time    timestamp default CURRENT_TIMESTAMP not null,
    user_email     varchar(50)                         not null
        references hb.users (user_email),
    category_id    uuid,
    description    text
);

