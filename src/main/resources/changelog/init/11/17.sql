--changeset author:stepanov-17-11-alter-role
alter table role alter column privilege_name drop not null;
alter table issue drop column is_returned;
alter table issue add column date_of_return date;
alter table issue alter column date_of_issue drop not null;
alter table issue alter column return_until drop not null;