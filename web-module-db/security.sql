DROP TABLE IF EXISTS users_roles cascade;
DROP TABLE IF EXISTS users_security cascade;
DROP TABLE IF EXISTS roles cascade;

CREATE TABLE IF NOT EXISTS users_security (
  id bigserial  NOT NULL,
  login varchar(255) UNIQUE,
  password varchar(255),
  user_id bigint,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS roles (
  id bigserial  NOT NULL,
  role_name varchar(100),
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users_roles(
  users_security_id bigint NOT NULL,
  roles_id bigint NOT NULL,
  PRIMARY KEY (users_security_id, roles_id),
  FOREIGN KEY (roles_id) REFERENCES roles(id),
  FOREIGN KEY (users_security_id) REFERENCES users_security(id)
);

INSERT INTO users_security(login, password, user_id)
values ('Roman24', '$2a$08$CVOdRl2OIjBNR3jcQJN6fOGuuqatlpWghwcezZXYhujyroyhOfhk2', 1);
--123456
INSERT INTO users_security(login, password, user_id)
values ('Alex35', '$2a$08$Ylu1iJe9znyzahEuadgTweJEpKx8Pn6g4LVYcETEukULHXs2qZ6K6', 2);
--987654

INSERT INTO roles (role_name)  values ('Admin');
INSERT INTO roles (role_name)  values ('Client');
INSERT INTO roles (role_name)  values ('Blocked');

INSERT INTO users_roles(users_security_id, roles_id) values(1,1);
INSERT INTO users_roles(users_security_id, roles_id) values(2,2);
