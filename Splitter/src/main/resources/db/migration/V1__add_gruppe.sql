create table gruppe(
    id          serial primary key,
    name        varchar(300),
    geschlossen boolean,
    personen    VARCHAR ARRAY
);

create table rechnung
(
    id            serial primary key,
    name varchar,
    money         DECIMAL,
    payer         varchar,
    persons       VARCHAR ARRAY,
    gruppe      integer
        constraint gruppe
            references gruppe
);