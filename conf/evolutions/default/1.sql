# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table gamer (
  id                        integer not null,
  name                      varchar(255),
  constraint pk_gamer primary key (id))
;

create sequence gamer_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists gamer;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists gamer_seq;

