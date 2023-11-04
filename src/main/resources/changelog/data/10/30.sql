--liquibase formatted sql
--changeset author:kaspshitskii-30-10-data-privilege
INSERT INTO privilege
 (id,privilege_name,registry,levels) VALUES
 (1,'SYPER_BOSSS','ALL','ALL'),
 (2,'CLIENT','CLIENT','SELECT'),
 (3,'CLIENT','CLIENT','CUD'),
 (4,'BOOK','BOOK','SELECT'),
 (5,'BOOK','BOOK','CUD'),
 (6,'AUTHOR','AUTHOR','SELECT'),
 (7,'AUTHOR','AUTHOR','CUD'),
 (8,'REPORTS','REPORTS','ALL'),
 (9,'ISSUES','ISSUES','ALL');

INSERT INTO public."role" (id,role_name,privilege_name) VALUES
       (1,'Админ','Админ'),
       (2,'Оператор стойки','Оператор стойки'),
       (3,'Оператор книгооборота','Оператор книгооборота'),
       (4,'Управляющий','Управляющий');

INSERT INTO public.roles_privilege (privilege_id,role_id) VALUES
     (1,1),
     (2,2),
     (3,2),
     (4,2),
     (9,2),
     (4,3),
     (5,3),
     (6,3),
     (7,3),
     (2,4),
     (4,4),
     (8,4);
