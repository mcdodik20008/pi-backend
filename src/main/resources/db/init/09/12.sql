--liquibase formatted sql
--changeset author:kaspshitskii-12-09-add-book
create table book
(
    uuid               varchar(128) not null,
    title              varchar(500) not null,
    subtitle           varchar(500),
    first_publish_date varchar(20),
    description        text,
    constraint PK_book_uuid primary key (uuid)
);

--changeset author:kaspshitskii-12-09-add-author
create table author
(
    uuid        varchar(128) not null,
    author_name varchar(100) not null,
    bio         text,
    birth_date  varchar(20),
    death_date  varchar(20),
    wikipedia   varchar(255),
    constraint PK_author_uuid primary key (uuid)
);

create table book_author
(
    book_id varchar(128),
    author_id varchar(128),
    constraint FK_book_author foreign key (book_id) references book,
    constraint FK_author_book foreign key (author_id) references author
);

--changeset author:kaspshitskii-12-09-add-book-cover
create table book_cover
(
    id int8,
    cover_file int4,
    book_id varchar(128),
    constraint PK_bookcover primary key (id),
    constraint FK_bookcover_book foreign key (book_id) references book
);