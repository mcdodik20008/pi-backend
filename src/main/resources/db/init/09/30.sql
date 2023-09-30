create table user_account
(
    id       int8,
    username varchar(100),
    password varchar(64),
    constraint PK_user_account primary key (id)
);

create table user_account_role
(
    id              int8,
    user_account_id int8,
    role_id         int8,
    constraint FK_user_account_id foreign key (user_account_id) references user_account,
    constraint FK_role_id foreign key (role_id) references role,
    constraint PK_user_account_role primary key (id)
);