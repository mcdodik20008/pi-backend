--liquibase formatted sql
--changeset author:kaspshitskii-12-09-add-book
create sequence book_id_sqns start 1000;

create table book
(
    id                 text primary key default 'B'||nextval('book_id_sqns'),
    title              varchar(500) not null,
    sub_title          varchar(500),
    first_publish_date varchar(20),
    description        text
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

--changeset author:kaspshitskii-12-09-add-book-subject
create table book_subject
(
    id int8,
    subject varchar(400),
    book_id varchar(128),
    constraint PK_booksubject primary key (id),
    constraint FK_booksubject_book foreign key (book_id) references book
);