CREATE TABLE board
(
  id      INT AUTO_INCREMENT PRIMARY KEY,
  board   VARCHAR(30) NOT NULL,
  user_id INT         NULL
);

CREATE INDEX board_users_id_fk
  ON board (user_id);

CREATE TABLE card
(
  id      INT AUTO_INCREMENT PRIMARY KEY,
  card    TEXT NOT NULL,
  id_list INT  NULL
);

CREATE INDEX card_list_id_fk
  ON card (id_list);

CREATE TABLE list
(
  id       INT AUTO_INCREMENT PRIMARY KEY,
  list     VARCHAR(30) NOT NULL,
  id_board INT         NULL,
  CONSTRAINT list_board_id_fk
  FOREIGN KEY (id_board) REFERENCES board (id)
    ON DELETE CASCADE
);

CREATE INDEX list_board_id_fk
  ON list (id_board);

ALTER TABLE card
  ADD CONSTRAINT card_list_id_fk
FOREIGN KEY (id_list) REFERENCES list (id)
  ON DELETE CASCADE;

CREATE TABLE users
(
  id                 INT AUTO_INCREMENT PRIMARY KEY,
  login              VARCHAR(30)                                         NOT NULL,
  password           VARCHAR(30)                                         NOT NULL,
  email              VARCHAR(60)                                         NOT NULL,
  dateOfRegistration DATE                                                NOT NULL,
  sex                ENUM ('man', 'woman')                               NULL,
  dateOfBirth        DATE                                                NULL,
  userStatus         ENUM ('user', 'admin', 'userStatus') DEFAULT 'user' NULL,
  firstname          VARCHAR(30) DEFAULT ''                         NULL,
  secondname         VARCHAR(30) DEFAULT ''                         NULL,
  contry             VARCHAR(30) DEFAULT ''                         NULL,
  city               VARCHAR(30) DEFAULT ''                         NULL,
  CONSTRAINT users_login_uindex
  UNIQUE (login),
  CONSTRAINT users_email_uindex
  UNIQUE (email)
);

ALTER TABLE board
  ADD CONSTRAINT board_users_id_fk
FOREIGN KEY (user_id) REFERENCES users (id)
  ON DELETE CASCADE;

