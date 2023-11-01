--changeset author:Gerasimov-22-10-add-customer
create sequence customer_id start 1000;

create table customer (
    id      text primary key default 'C'||nextval('customer_id'),
    name    varchar(255),
    address varchar(255),
    zip     varchar(255),
    city    varchar(255),
    phone   varchar(255),
    email   varchar(255)
);