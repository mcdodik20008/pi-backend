create table user_account
(
    id       int8,
    username varchar(100),
    password varchar(64),
    role_id  int8,
    constraint FK_user_account_role foreign key (role_id) references role,
    constraint PK_user_account primary key (id)
);