CREATE TABLE IF NOT EXISTS users(
   id UUID NOT NULL,
   first_name varchar(255),
   last_name varchar(255),
   date_of_birth date,
   sex varchar(1),
   weight smallint,
   email varchar(255),
   PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS category_of_fitness_program(
    id UUID NOT NULL,
    short_name varchar(255),
    description varchar,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS fitness_program(
    id UUID NOT NULL,
    short_name varchar(255),
    duration smallint,
    age_restriction smallint,
    weight_restriction smallint,
    sex_restriction varchar(1),
    exercise_per_week smallint,
    description varchar,
    category_id UUID,
    PRIMARY KEY (id),
    FOREIGN KEY (category_id) REFERENCES category_of_fitness_program(id)
);

CREATE TABLE IF NOT EXISTS review(
    id UUID NOT NULL,
    author_id UUID,
    text varchar,
    fitness_program_id UUID,
    author_name varchar,
    PRIMARY KEY (id),
    FOREIGN KEY (fitness_program_id) REFERENCES fitness_program(id)
);

CREATE TABLE IF NOT EXISTS bookmark(
    id UUID NOT NULL,
    fitness_program_id UUID,
    user_id UUID,
    PRIMARY KEY (id),
    FOREIGN KEY (fitness_program_id) REFERENCES fitness_program(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS active_program(
    id UUID NOT NULL,
    days varchar,
    is_complited boolean,
    fitness_program_id UUID,
    user_id UUID,
    PRIMARY KEY (id),
    FOREIGN KEY (fitness_program_id) REFERENCES fitness_program(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS exercise_schedule(
    id UUID NOT NULL,
    exercise_date date,
    is_complited boolean,
    active_program_id UUID,
    program_short_name varchar,
    FOREIGN KEY (active_program_id) REFERENCES active_program(id)
);

INSERT INTO users (id, first_name, last_name, date_of_birth, sex, weight, email)
values ('00000000-0000-0000-0000-000000000001', 'TestName', 'TestSurname', '1980-03-12','m',86,'petrIvanov@mail.ru');
INSERT INTO users (id, first_name, last_name, date_of_birth, sex, weight, email)
values ('00000000-0000-0000-0000-000000000002', 'TestName', 'TestSurname', '1985-04-22','f',55,'olyaMak@mail.ru');
INSERT INTO users (id, first_name, last_name, date_of_birth, sex, weight, email)
values ('00000000-0000-0000-0000-000000000003', 'Andrey', 'Andreev', '1993-12-24','m',80,'admin@mail.ru');
INSERT INTO users (id, first_name, last_name, date_of_birth, sex, weight, email)
values ('00000000-0000-0000-0000-000000000004', 'Pete', 'Smirnov', '1995-10-25','m',60,'pete@mail.ru');

INSERT INTO category_of_fitness_program(id, short_name, description)
values('00000000-0000-0000-0000-000000000005', 'TestCategory1', 'Description of TestCategory1');
INSERT INTO category_of_fitness_program(id, short_name, description)
values('00000000-0000-0000-0000-000000000006', 'TestCategory2', 'Description of TestCategory2');

INSERT INTO fitness_program(id, short_name, duration, age_restriction, weight_restriction, sex_restriction,
                            exercise_per_week, description, category_id)
values('00000000-0000-0000-0000-000000000007', 'TestProgram1', 10, 60, 90, 'm',2,'Description of TestProgram1',
       '00000000-0000-0000-0000-000000000005');
INSERT INTO fitness_program(id, short_name, duration, age_restriction, weight_restriction, sex_restriction,
                            exercise_per_week, description, category_id)
values('00000000-0000-0000-0000-000000000008', 'TestProgram2', 10, 30, 90, 'm',2,'Description of TestProgram2',
       '00000000-0000-0000-0000-000000000005');
INSERT INTO fitness_program(id, short_name, duration, age_restriction, weight_restriction, sex_restriction,
                            exercise_per_week, description, category_id)
values('00000000-0000-0000-0000-000000000009', 'TestProgram3', 10, 60, 70, 'm',2,'Description of TestProgram3',
       '00000000-0000-0000-0000-000000000005');
INSERT INTO fitness_program(id, short_name, duration, age_restriction, weight_restriction, sex_restriction,
                            exercise_per_week, description, category_id)
values('00000000-0000-0000-0000-000000000010', 'TestProgram4', 10, 40, 90, 'f',3,'Description of TestProgram1',
       '00000000-0000-0000-0000-000000000006');
INSERT INTO fitness_program(id, short_name, duration, age_restriction, weight_restriction, sex_restriction,
                            exercise_per_week, description, category_id)
values('00000000-0000-0000-0000-000000000011', 'TestProgram5', 10, 30, 90, 'f',3,'Description of TestProgram2',
       '00000000-0000-0000-0000-000000000006');
INSERT INTO fitness_program(id, short_name, duration, age_restriction, weight_restriction, sex_restriction,
                            exercise_per_week, description, category_id)
values('00000000-0000-0000-0000-000000000012', 'TestProgram6', 10, 40, 50, 'f',2,'Description of TestProgram3',
       '00000000-0000-0000-0000-000000000006');

INSERT INTO active_program(id, days, is_complited, fitness_program_id, user_id)
VALUES ('00000000-0000-0000-0000-000000000013',  'testDays1',true,'00000000-0000-0000-0000-000000000007',
        '00000000-0000-0000-0000-000000000001');
INSERT INTO active_program(id, days, is_complited, fitness_program_id, user_id)
VALUES ('00000000-0000-0000-0000-000000000014', 'testDays2', true,'00000000-0000-0000-0000-000000000008',
        '00000000-0000-0000-0000-000000000001');
INSERT INTO active_program(id, days, is_complited, fitness_program_id, user_id)
VALUES ('00000000-0000-0000-0000-000000000015', 'testDays3', true,'00000000-0000-0000-0000-000000000009',
        '00000000-0000-0000-0000-000000000001');
INSERT INTO active_program(id, days, is_complited, fitness_program_id, user_id)
VALUES ('00000000-0000-0000-0000-000000000016', 'testDays4', false,'00000000-0000-0000-0000-000000000007',
        '00000000-0000-0000-0000-000000000001');
INSERT INTO active_program(id, days, is_complited, fitness_program_id, user_id)
VALUES ('00000000-0000-0000-0000-000000000017', '', false,'00000000-0000-0000-0000-000000000007',
        '00000000-0000-0000-0000-000000000004');
INSERT INTO active_program(id, days, is_complited, fitness_program_id, user_id)
VALUES ('00000000-0000-0000-0000-000000000018', '', false,'00000000-0000-0000-0000-000000000012',
        '00000000-0000-0000-0000-000000000003');

INSERT INTO exercise_schedule(id, exercise_date, is_complited, active_program_id)
VALUES ('00000000-0000-0000-0000-000000000019', '2021-01-01',false, '00000000-0000-0000-0000-000000000013');
INSERT INTO exercise_schedule(id, exercise_date, is_complited, active_program_id)
VALUES ('00000000-0000-0000-0000-000000000020', '2011-04-01',false, '00000000-0000-0000-0000-000000000013');
INSERT INTO exercise_schedule(id, exercise_date, is_complited, active_program_id)
VALUES ('00000000-0000-0000-0000-000000000021', '2021-05-25',false, '00000000-0000-0000-0000-000000000013');
INSERT INTO exercise_schedule(id, exercise_date, is_complited, program_short_name, active_program_id)
VALUES ('00000000-0000-0000-0000-000000000022', '2021-01-01',false, 'Test4Name','00000000-0000-0000-0000-000000000013');
INSERT INTO exercise_schedule(id, exercise_date, is_complited, active_program_id)
VALUES ('00000000-0000-0000-0000-000000000023', '2021-01-05',false, '00000000-0000-0000-0000-000000000017');

INSERT INTO review(id, author_id, text, fitness_program_id)
VALUES ('00000000-0000-0000-0000-000000000024','00000000-0000-0000-0000-000000000001', 'Test review 1', '00000000-0000-0000-0000-000000000007');
INSERT INTO review(id, author_id, text, fitness_program_id)
VALUES ('00000000-0000-0000-0000-000000000025','00000000-0000-0000-0000-000000000001', 'Test review 2', '00000000-0000-0000-0000-000000000008');
INSERT INTO review(id, author_id, text, fitness_program_id)
VALUES ('00000000-0000-0000-0000-000000000026','00000000-0000-0000-0000-000000000001', 'Test review 3', '00000000-0000-0000-0000-000000000009');

INSERT INTO bookmark(id, fitness_program_id, user_id)
VALUES ('00000000-0000-0000-0000-000000000027', '00000000-0000-0000-0000-000000000007', '00000000-0000-0000-0000-000000000001');
INSERT INTO bookmark(id, fitness_program_id, user_id)
VALUES ('00000000-0000-0000-0000-000000000028', '00000000-0000-0000-0000-000000000008', '00000000-0000-0000-0000-000000000001');