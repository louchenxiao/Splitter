create table gruppe(
    id          serial primary key,
    name        varchar(300),
    geschlossen boolean,
    personen    character varying[]
);

create table rechnung
(
    id            serial primary key,
    name varchar,
    money         money,
    payer         varchar,
    persons       character varying[],
    gruppe      integer
        constraint gruppe
            references gruppe
);