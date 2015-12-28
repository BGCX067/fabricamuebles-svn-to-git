INSERT INTO Permission (NAME, DESCRIPTION) VALUES ("ADM_ROL","Administrar Roles");
INSERT INTO Permission (NAME, DESCRIPTION) VALUES ("ADM_PERM","Administrar Permisos");
INSERT INTO Permission (NAME, DESCRIPTION) VALUES ("ADM_USER","Administrar Usuarios");
INSERT INTO Role (ID,UID,NAME,DESCRIPTION,DATECREATED,LASTMODIFIED,VERSION) VALUES (1,"1","Administrador","Administrador del Sistema",now(),now(),1);
INSERT INTO Role_Permission (ROLE_ID,PERM_NAME,ROLE_PERM_IDX) values (1,"ADM_USER",2);
INSERT INTO Role_Permission (ROLE_ID,PERM_NAME,ROLE_PERM_IDX) values (1,"ADM_ROL",0);
INSERT INTO Role_Permission (ROLE_ID,PERM_NAME,ROLE_PERM_IDX) values (1,"ADM_PERM",1);
-- la password es "password"
INSERT INTO User (ID,UID,FIRSTNAME,LASTNAME,USERNAME,PASSWORD,DATECREATED,LASTMODIFIED,VERSION) VALUES (1,"1","Admin","Admin","admin","ea023d53a614f1be9a63c2b3e5373d77e7142279",now(),now(),1);
INSERT INTO User_Role (USER_ID,ROLE_ID,USER_ROLE_IDX) VALUES (1,1,0);
INSERT INTO hibernate_sequences (sequence_name, sequence_next_hi_value) values ('Role',1);
INSERT INTO hibernate_sequences (sequence_name, sequence_next_hi_value) values ('User',1);