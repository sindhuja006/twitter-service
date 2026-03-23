CREATE TABLE IF NOT EXISTS users (
    userId varchar primary key ,
    name varchar(50) not null ,
    email varchar(100) not null unique
);