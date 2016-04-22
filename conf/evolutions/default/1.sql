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
  id                        bigint not null,
  title                     varchar(255),
  member_of_name            varchar(255),
  room_id                   bigint,
  constraint pk_team primary key (id))
;

create sequence player_seq;

create sequence room_seq;

create sequence team_seq;

alter table team add constraint fk_team_memberOf_1 foreign key (member_of_name) references player (name) on delete restrict on update restrict;
create index ix_team_memberOf_1 on team (member_of_name);
alter table team add constraint fk_team_room_2 foreign key (room_id) references room (id) on delete restrict on update restrict;
create index ix_team_room_2 on team (room_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists player;

drop table if exists room;

drop table if exists team;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists player_seq;

drop sequence if exists room_seq;

drop sequence if exists team_seq;

