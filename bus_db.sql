drop database busdb;
drop user busmanager;
create user busmanager with password 'password';
create database busdb with template=template0 owner=busmanager;
\connect busdb;
alter default privileges grant all on tables to busmanager;


create table bus(
id varchar(20) not null
);