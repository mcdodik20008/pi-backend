--liquibase formatted sql
--changeset author:kaspshitskii
create table book(
    uuid varchar(128) not null,
    title varchar(500) not null,
    subtitle varchar(500),
    first_publish_date varchar(20),
    description text,
    constraint PK_uuid primary key (uuid)
);
