DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS category_of_fitness_program;
DROP TABLE IF EXISTS fitness_program;
DROP TABLE IF EXISTS exercise_schedule;
DROP TABLE IF EXISTS bookmark;
DROP TABLE IF EXISTS active_program;
DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users(
   id long NOT NULL AUTO_INCREMENT,
   first_name varchar(255),
   last_name varchar(255),
   date_of_birth date,
   sex varchar(1),
   weight smallint,
   email varchar(255),
   PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS category_of_fitness_program(
    id long NOT NULL auto_increment,
    short_name varchar(255),
    description varchar,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS fitness_program(
    id long NOT NULL auto_increment,
    short_name varchar(255),
    duration smallint,
    age_restriction smallint,
    weight_restriction smallint,
    sex_restriction varchar(1),
    exercise_per_week smallint,
    description varchar,
    category_id bigint,
    PRIMARY KEY (id),
    FOREIGN KEY (category_id) REFERENCES category_of_fitness_program(id)
);

CREATE TABLE IF NOT EXISTS review(
    id long NOT NULL auto_increment,
    author_id bigint,
    text varchar,
    fitness_program_id bigint,
    PRIMARY KEY (id),
    FOREIGN KEY (fitness_program_id) REFERENCES fitness_program(id)
);

CREATE TABLE IF NOT EXISTS bookmark(
    id long NOT NULL auto_increment,
    fitness_program_id bigint,
    user_id bigint,
    PRIMARY KEY (id),
    FOREIGN KEY (fitness_program_id) REFERENCES fitness_program(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS active_program(
    id long NOT NULL auto_increment,
    days varchar,
    is_complited boolean,
    fitness_program_id bigint,
    user_id bigint,
    PRIMARY KEY (id),
    FOREIGN KEY (fitness_program_id) REFERENCES fitness_program(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS exercise_schedule(
    id long NOT NULL auto_increment,
    exercise_date date,
    is_complited boolean,
    active_program_id bigint,
    FOREIGN KEY (active_program_id) REFERENCES active_program(id)
);

INSERT INTO users (first_name, last_name, date_of_birth, sex, weight, email)
values ('TestName', 'TestSurname', '1980-03-12','m',86,'petrIvanov@mail.ru');
INSERT INTO users (first_name, last_name, date_of_birth, sex, weight, email)
values ('TestName', 'TestSurname', '1985-04-22','f',55,'olyaMak@mail.ru');
INSERT INTO users (first_name, last_name, date_of_birth, sex, weight, email)
values ('Andrey', 'Andreev', '1993-12-24','m',80,'admin@mail.ru');

INSERT INTO category_of_fitness_program(short_name, description) values('TestCategory1', 'Description of TestCategory1');
INSERT INTO category_of_fitness_program(short_name, description) values('TestCategory2', 'Description of TestCategory2');

INSERT INTO fitness_program(short_name, duration, age_restriction, weight_restriction, sex_restriction, exercise_per_week,
                            description, category_id)
values('TestProgram1', 10, 60, 90, 'm',2,'Description of TestProgram1',1);
INSERT INTO fitness_program(short_name, duration, age_restriction, weight_restriction, sex_restriction, exercise_per_week,
                            description, category_id)
values('TestProgram2', 10, 30, 90, 'm',2,'Description of TestProgram2',1);
INSERT INTO fitness_program(short_name, duration, age_restriction, weight_restriction, sex_restriction, exercise_per_week,
                            description, category_id)
values('TestProgram3', 10, 60, 70, 'm',2,'Description of TestProgram3',1);
INSERT INTO fitness_program(short_name, duration, age_restriction, weight_restriction, sex_restriction, exercise_per_week,
                            description, category_id)
values('TestProgram1', 10, 40, 90, 'f',3,'Description of TestProgram1',2);
INSERT INTO fitness_program(short_name, duration, age_restriction, weight_restriction, sex_restriction, exercise_per_week,
                            description, category_id)
values('TestProgram2', 10, 30, 90, 'f',3,'Description of TestProgram2',2);
INSERT INTO fitness_program(short_name, duration, age_restriction, weight_restriction, sex_restriction, exercise_per_week,
                            description, category_id)
values('TestProgram3', 10, 40, 50, 'f',3,'Description of TestProgram3',2);

INSERT INTO active_program(days, is_complited, fitness_program_id, user_id) VALUES ( 'testDays1',true,1,1 );
INSERT INTO active_program(days, is_complited, fitness_program_id, user_id) VALUES ( 'testDays2',true,2,1 );
INSERT INTO active_program(days, is_complited, fitness_program_id, user_id) VALUES ( 'testDays3',true,3,1 );
INSERT INTO active_program(days, is_complited, fitness_program_id, user_id) VALUES ( 'testDays4',false,1,1 );

INSERT INTO exercise_schedule(exercise_date, is_complited) values ('2021-01-01',false);
INSERT INTO exercise_schedule(exercise_date, is_complited) values ('2011-04-01',false);
INSERT INTO exercise_schedule(exercise_date, is_complited) values ('2021-05-25',false);
INSERT INTO exercise_schedule(exercise_date, is_complited) values ('2021-01-01',false);