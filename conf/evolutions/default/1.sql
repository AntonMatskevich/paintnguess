# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table gamer (
  id                        integer not null,
  name                      varchar(255),
  constraint pk_gamer primary key (id))
;

create table player (
  name                      varchar(255) not null,
  password                  varchar(255),
  constraint pk_player primary key (name))
;

create table room (
  id                        bigint not null,
  name                      varchar(255),
  folder                    varchar(255),
  constraint pk_room primary key (id))
;

create table team (
  id                        bigint not null,
  title                     varchar(255),
  done                      boolean,
  due_date                  timestamp,
  assigned_to_name          varchar(255),
  folder                    varchar(255),
  room_id                   bigint,
  constraint pk_team primary key (id))
;


create table room_player (
  room_id                        bigint not null,
  player_name                    varchar(255) not null,
  constraint pk_room_player primary key (room_id, player_name))
;
create sequence gamer_seq;

create sequence player_seq;

create sequence room_seq;

create sequence team_seq;

alter table team add constraint fk_team_assignedTo_1 foreign key (assigned_to_name) references player (name) on delete restrict on update restrict;
create index ix_team_assignedTo_1 on team (assigned_to_name);
alter table team add constraint fk_team_room_2 foreign key (room_id) references room (id) on delete restrict on update restrict;
create index ix_team_room_2 on team (room_id);



alter table room_player add constraint fk_room_player_room_01 foreign key (room_id) references room (id) on delete restrict on update restrict;

alter table room_player add constraint fk_room_player_player_02 foreign key (player_name) references player (name) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists gamer;

drop table if exists player;

drop table if exists room;

drop table if exists room_player;

drop table if exists team;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists gamer_seq;

drop sequence if exists player_seq;

drop sequence if exists room_seq;

drop sequence if exists team_seq;

