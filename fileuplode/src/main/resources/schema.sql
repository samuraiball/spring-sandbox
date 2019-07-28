drop table if exists files;
drop table if exists id_generator;

create table
    files
(
    file_id   integer primary key,
    file_name varchar(100),
    mime_type varchar(36),
    file_body blob
);

create table
    id_generator
(
    pk    varchar(100),
    value integer
);

insert into id_generator (pk, value)
values ('file_id', 0);

