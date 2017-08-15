CREATE TABLE users
(
  id              INT AUTO_INCREMENT
    PRIMARY KEY,
  login           VARCHAR(20)            NULL,
  password        VARCHAR(15)            NULL,
  email           VARCHAR(40)            NULL,
  date_registered DATE                   NOT NULL,
  sex             ENUM ('man', 'woman')  NULL,
  date_birth      DATE                   NULL,
  block           TINYINT(1) DEFAULT '0' NULL,
  firstname       VARCHAR(25)            NULL,
  secondname      VARCHAR(25)            NULL,
  contry          VARCHAR(40)            NULL,
  city            VARCHAR(40)            NULL,
  CONSTRAINT users_login_uindex
  UNIQUE (login)
);

CREATE TABLE board
(
  id      INT AUTO_INCREMENT
    PRIMARY KEY,
  board   VARCHAR(30) NOT NULL,
  user_id INT         NULL,
  CONSTRAINT board_users_id_fk
  FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE INDEX board_users_id_fk
  ON board (user_id);

CREATE TABLE list
(
  id       INT AUTO_INCREMENT
    PRIMARY KEY,
  list     VARCHAR(40) NOT NULL,
  id_board INT         NULL,
  CONSTRAINT list_board_id_fk
  FOREIGN KEY (id_board) REFERENCES board (id)
);

CREATE INDEX list_board_id_fk
  ON list (id_board);

CREATE TABLE card
(
  id      INT AUTO_INCREMENT
    PRIMARY KEY,
  card    VARCHAR(40) NOT NULL,
  id_list INT         NULL,
  CONSTRAINT card_list_id_fk
  FOREIGN KEY (id_list) REFERENCES list (id)
);

CREATE INDEX card_list_id_fk
  ON card (id_list);



