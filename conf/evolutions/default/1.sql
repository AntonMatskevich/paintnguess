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

create sequence player_seq;

create sequence room_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists player;

drop table if exists room;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists player_seq;

drop sequence if exists room_seq;

