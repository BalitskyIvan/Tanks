create schema if not exists tanks;

drop table if exists tanks.users;

create table tanks.users (
   id int generated always as identity,
   username varchar(30) not null unique,
   password varchar(100) not null,

   primary key (id)
);

drop table if exists tanks.statistics;

create table tanks.statistics (
    id int generated always as identity,
    username varchar(30) not null,
    enemyname varchar(30) not null,
    shots int,
    hits int,
    misses int,

    primary key(id)
)