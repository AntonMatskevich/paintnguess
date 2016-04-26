# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table player (
  name                      varchar(255) not null,
  id                        bigint,
  password                  varchar(255),
  constraint pk_player primary key (name))
;

create table room (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_room primary key (id))
;

create table team (
  title                     varchar(255) not null,
  id                        bigint,
  room                      bigint,
  constraint pk_team primary key (title))
;

create sequence player_seq;

create sequence room_seq;

create sequence team_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists player;

drop table if exists room;

drop table if exists team;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists player_seq;

drop sequence if exists room_seq;

drop sequence if exists team_seq;

