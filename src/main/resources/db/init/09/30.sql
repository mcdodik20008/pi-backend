create table user_account
(
    user_login       varchar(255),
    user_password    varchar(64),
    user_first_name  varchar(255),
    user_second_name varchar(255),
    user_last_name   varchar(255),
    constraint PK_user_account primary key (user_login)
);

create table user_account_role
(
    user_account_id varchar(50),
    role_id         int8,
    constraint FK_user_account_id foreign key (user_account_id) references user_account,
    constraint FK_role_id foreign key (role_id) references role
);
