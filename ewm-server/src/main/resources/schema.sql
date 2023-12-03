create table if not exists users
(
    id    serial
        constraint user_pk
            primary key,
    email varchar(255) not null
        constraint users_pk
            unique,
    name  varchar(255) not null
);

create table if not exists request
(
    id           serial
        constraint request_pk
            primary key,
    event_id     integer     not null
        constraint request_event_id_fk
            references event
            on update restrict on delete restrict,
    created      timestamp   not null,
    requester_id integer     not null
        constraint request_user_id_fk
            references users,
    status       varchar(32) not null
);

create table if not exists event_compilation_connection
(
    id             integer not null
        constraint event_compilation_connection_pk
            primary key,
    event_id       integer not null
        constraint event_compilation_connection_event_id_fk
            references event
            on update cascade on delete cascade,
    compilation_id integer not null
        constraint event_compilation_connection_compilation_id_fk
            references compilation
            on update cascade on delete cascade
);

create table if not exists event
(
    id                 serial
        constraint event_pk
            primary key,
    annotation         varchar(2000)    not null,
    description        varchar(7000)    not null,
    category_id        integer          not null
        constraint event_category_id_fk
            references category
            on update cascade on delete cascade,
    created            timestamp        not null,
    event_date         timestamp        not null,
    initiator_id       integer          not null
        constraint event_user_id_fk
            references users
            on update cascade on delete cascade,
    paid               boolean          not null,
    participant_limit  integer default 0,
    published          timestamp,
    request_moderation boolean default true,
    state              varchar(32)      not null,
    title              varchar(128)     not null,
    views              integer          not null,
    lat                double precision not null,
    lon                double precision not null
);

create table if not exists compilation
(
    id     serial
        constraint compilation_pk
            primary key,
    pinned boolean      not null,
    title  varchar(128) not null
        constraint compilation_pk2
            unique
);

create table if not exists category
(
    id   serial
        constraint "CATEGORY_pk"
            primary key,
    name varchar(50) not null
        constraint category_pk
            unique
);

alter table category
    owner to root;

alter table compilation
    owner to root;

alter table event
    owner to root;

alter table event_compilation_connection
    owner to root;

alter table request
    owner to root;

alter table users
    owner to root;