--changeset author:kaspshitskii-12-09-add-roles
create table role
(
    id        int8,
    role_name varchar(400) not null,
    constraint PK_role primary key (id)
);

create table privilege
(
    id             int8,
    privilege_name varchar(400) not null,
    constraint PK_privilege primary key (id)
);

create table roles_privilege
(
    privilege_id int8,
    role_id      int8,
    constraint FK_privilege_role foreign key (privilege_id) references privilege,
    constraint FK_role_privilege foreign key (role_id) references role
);