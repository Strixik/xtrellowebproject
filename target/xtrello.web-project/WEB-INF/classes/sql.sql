create table users
(
  id int auto_increment
    primary key,
  login varchar(20) null,
  password varchar(15) null,
  email varchar(40) null,
  date_registered date not null,
  sex enum('man', 'woman') null,
  date_birth date null,
  block tinyint(1) default '0' null,
  firstName varchar(25) null,
  secondName varchar(25) null,
  country varchar(40) null,
  city varchar(40) null,
  constraint users_login_uindex
  unique (login)
)
;

create table board
(
  id int auto_increment
    primary key,
  board varchar(30) not null,
  userId int null,
  constraint board_users_id_fk
  foreign key (userId) references users (id)
    on delete cascade
)
;

create index board_users_id_fk
  on board (userId)
;

create table list
(
  id int auto_increment
    primary key,
  list varchar(40) not null,
  id_board int null,
  constraint list_board_id_fk
  foreign key (id_board) references board (id)
    on delete cascade
)
;

create index list_board_id_fk
  on list (id_board)
;

create table card
(
  id int auto_increment
    primary key,
  card varchar(40) not null,
  id_list int null,
  constraint card_list_id_fk
  foreign key (id_list) references list (id)
    on delete cascade
)
;

create index card_list_id_fk
  on card (id_list)
;




