-- Todos
create table IF NOT EXISTS todos (
    id serial primary key ,
    title varchar(255) not null,
    completed boolean not null
);