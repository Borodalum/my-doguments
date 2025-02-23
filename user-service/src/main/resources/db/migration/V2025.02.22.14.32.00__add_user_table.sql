create table users(
    id          serial          primary key,
    login       varchar(128)    not null,
    email       varchar(128)    not null,
    password    varchar(128)    not null
);
