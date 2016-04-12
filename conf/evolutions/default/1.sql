# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table gamer (
  id                        integer not null,
  name                      varchar(255),
  constraint pk_gamer primary key (id))
;

create table player (
  player_name               varchar(255) not null,
  password                  varchar(255),
  constraint pk_player primary key (player_name))
;

create table room (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_room primary key (id))
;


create table room_player (
  room_id                        bigint not null,
  player_player_name             varchar(255) not null,
  constraint pk_room_player primary key (room_id, player_player_name))
;
create sequence gamer_seq;

create sequence player_seq;

create sequence room_seq;




alter table room_player add constraint fk_room_player_room_01 foreign key (room_id) references room (id) on delete restrict on update restrict;

alter table room_player add constraint fk_room_player_player_02 foreign key (player_player_name) references player (player_name) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists gamer;

drop table if exists player;

drop table if exists room;

drop table if exists room_player;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists gamer_seq;

drop sequence if exists player_seq;

drop sequence if exists room_seq;

