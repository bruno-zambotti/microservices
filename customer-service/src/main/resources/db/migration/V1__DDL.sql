create table address (
    id integer not null,
    city varchar(255),
    complement varchar(255),
    country varchar(255),
    number integer,
    postal_code varchar(255),
    province varchar(255),
    street varchar(255),
    type varchar(255),
    customer_id integer,
    primary key (id)
);

create table customer (
    id integer not null,
    birth_date timestamp,
    gender char(255) not null,
    name varchar(255),
    surname varchar(255),
    primary key (id)
);

create table phone (
    customer_id integer not null,
    phones varchar(255)
);

create table user_auth (
    id integer not null,
    password varchar(255),
    username varchar(255),
    primary key (id)
);

alter table address
    add constraint FK93c3js0e22ll1xlu21nvrhqgg
    foreign key (customer_id)
    references customer;

alter table phone
    add constraint FKgvgc4f2c3qkiswyv6bbs0br1p
    foreign key (customer_id)
    references customer;

create sequence user_sequence
  start with 1
  increment by 1;

create sequence address_sequence
  start with 1
  increment by 1;

create sequence customer_sequence
  start with 1
  increment by 1;