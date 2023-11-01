--changeset author:stepanov-26-10-add-issue
create table issue (
    id      int8,
    date_of_issue    date not null,
    return_until date not null,
    customer_id     varchar(255),
    book_id    varchar(255),
    primary key (id),
    foreign key (customer_id) references customer,
    foreign key (book_id) references book
)