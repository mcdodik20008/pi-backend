create table user_account
(
    id       int8,
    username varchar(100),
    password varchar(64),
    constraint PK_user_account primary key (id)
);