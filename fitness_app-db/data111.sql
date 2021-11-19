DROP TABLE IF EXISTS review cascade;
DROP TABLE IF EXISTS category_of_fitness_program cascade;
DROP TABLE IF EXISTS fitness_program cascade;
DROP TABLE IF EXISTS exercise_schedule cascade;
DROP TABLE IF EXISTS bookmark cascade;
DROP TABLE IF EXISTS active_program cascade;
DROP TABLE IF EXISTS users cascade;

CREATE TABLE IF NOT EXISTS users(
    id bigserial NOT NULL,
    first_name varchar(255),
    last_name varchar(255),
    date_of_birth date,
    sex varchar(1),
    weight smallint,
    email varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS category_of_fitness_program(
    id bigserial NOT NULL,
    short_name varchar(255),
    description varchar,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS fitness_program(
    id bigserial NOT NULL,
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
    id bigserial NOT NULL,
    author_name varchar,
    author_id bigint,
    text varchar,
    fitness_program_id bigint,
    PRIMARY KEY (id),
    FOREIGN KEY (fitness_program_id) REFERENCES fitness_program(id)
);

CREATE TABLE IF NOT EXISTS bookmark(
    id bigserial NOT NULL,
    fitness_program_id bigint,
    user_id bigint,
    PRIMARY KEY (id),
    FOREIGN KEY (fitness_program_id) REFERENCES fitness_program(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS active_program(
    id bigserial NOT NULL,
    days varchar,
    is_complited boolean,
    fitness_program_id bigint,
    user_id bigint,
    PRIMARY KEY (id),
    FOREIGN KEY (fitness_program_id) REFERENCES fitness_program(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS exercise_schedule(
    id bigserial NOT NULL,
    program_short_name varchar(255),
    exercise_date date,
    is_complited boolean,
    active_program_id bigint,
    FOREIGN KEY (active_program_id) REFERENCES active_program(id)
);

INSERT INTO category_of_fitness_program(short_name, description) values('Сушка','Сушка тела – комплекс мероприятий, ' ||
            'включающий специальную систему питания и физических упражнений, направленный на достижение подтянутого ' ||
            'рельефного тела. Специфическая система питания помогает быстрее сжечь жировую прослойку и нарастить мышцы. ' ||
            'Понятие «сушка» пришло к нам из направления бодибилдинга. Как правило, бодибилдеры начинают сушку за 2 ' ||
            'месяца до начала соревнований, чтобы просушить тело, придать ему форму и рельеф.');
INSERT INTO category_of_fitness_program(short_name, description) values('Укрепление здоровья',
            'Оптимальное решение для укрепления здоровья и обретения бодрости духа — аэробные нагрузки. Это такие ' ||
            'упражнения, при выполнении которых работает все тело. Они включают в работу все мышцы, связки, суставы и ' ||
            'кости, а также сердечно-сосудистую и дыхательную системы. В процессе нагрузки через них активно течет кровь, ' ||
            '«запуская» обменные процессы и вымывая продукты обмена. При этом активизируется работа внутренних органов, ' ||
            'и сжигаются лишние калории. Таким образом, происходит общее оздоравливающее воздействие на весь организм.');
INSERT INTO category_of_fitness_program(short_name, description) values('Улучшение жизненного тонуса, выносливости',
             'Выделяют два вида выносливости: общую (сердечно-сосудистую) и силовую (мышечную). В обоих случаях ' ||
             'подразумевается умеренная интенсивность, а не работа на износ. Но и она со временем повышается при ' ||
             'регулярных тренировках. Мышечная выносливость — способность мышцы прикладывать силу последовательно и ' ||
             'многократно в течение определенного периода времени. Она играет большую роль почти во всех спортивных ' ||
             'занятиях и влияет на общее самочувствие.');
INSERT INTO category_of_fitness_program(short_name, description) values('Силовые тренировки',
            'Силовые тренировки - это комплекс упражнений с постоянным увеличением весовой нагрузки, направленный на ' ||
            'укрепление скелетно-мышечной системы. Силовые упражнения - неотъемлемая часть любой тренировочной программы: ' ||
            'они способствуют развитию как мышечной силы, так и мышечной выносливости.');

INSERT INTO fitness_program(short_name, duration, age_restriction, weight_restriction, sex_restriction, exercise_per_week,
                            description, category_id)
            values('ноги и грудь', 23, 45, 96, 'm',3,'Приседания со штангой – 5 подходов по 12 повторений. Румынская тяга' ||
                   ' – 4 подхода по 15 повторений. Подъемы на носки – 4 подхода по 20 повторений. Жим лежа – 4 подхода ' ||
                   'по 12 повторений. Жим гантелей под углом – 4 подхода по 12 повторений. Разведение гантелей – ' ||
                                                     '4 подхода по 15 повторений',1);
INSERT INTO fitness_program(short_name, duration, age_restriction, weight_restriction, sex_restriction, exercise_per_week,
                            description, category_id)
            values('руки и пресс', 14, 37, 110, 'f',2,'Жим узким хватом – 4 подхода по 15 повторений. Подъемы на бицепс' ||
                   ' – 4 подхода по 15 повторений. Молотки – 5 подходов по 15 повторений. Французский жим стоя – 5 ' ||
                   'подходов по 15 повторений. Подъемы ногсв висе – 5 подходов по максимуму. Упражнение велосипед – 5 ' ||
                   'подходов по максимуму',1);
INSERT INTO fitness_program(short_name, duration, age_restriction, weight_restriction, sex_restriction, exercise_per_week,
                            description, category_id)
            values('Йога', 17, 60, 150, 'f',3,'1. Отдых. Сделайте 2–3 спокойных вдоха-выдоха, примите удобную позу, ' ||
                     'расслабьтесь. 2. Мысленная настройка. Внимательно посмотрите на изображение позы в книге, ' ||
                     'представьте себя в этой позе. 3. Вхождение в позу. Последовательно, плавно, спокойно войдите в ' ||
                     'позу согласно инструкции. Не торопитесь, не напрягайтесь. 4. Фиксация позы. На начальных этапах ' ||
                     'нельзя удерживать позу больше 5 секунд. Постепенно время фиксации можно увеличить до 20–30 секунд.' ||
                     ' Во время удержания позы надо думать о чем-нибудь хорошем. 5. Выход из позы. Старайтесь делать ' ||
                     'очень плавные движения при возвращении в исходное положение. 6. Отдых после выхода из позы. ' ||
                     'Запомните, что время отдыха составляет примерно одну четверть от общей продолжительности упражнения.' ||
                     ' То есть если вы позанимались сорок минут, обязательно полежите десять минут на спине,' ||
                     ' расслабьтесь.',2);
INSERT INTO fitness_program(short_name, duration, age_restriction, weight_restriction, sex_restriction, exercise_per_week,
                            description, category_id)
values('Увеличиваем бицепсы', 21, 50, 100, 'm',4,'Качаем бицепсы. Необходимо выполнить поочерёдно подъёмы гантелей, ' ||
                     'при этом меняются руки (три повтора по десять раз). Упражнение для плечевых мышц. Выполняем ' ||
                     'армейский жим гантелей (три подхода по десять раз каждый). Мышцы груди. Сделать отжимания от ' ||
                     'скамьи (наклонной). Количество подходов – такое же, как и в предыдущих упражнениях. Тренировка ' ||
                     'трицепса. Можно выполнять лёжа какие-либо знакомые вам упражнения для растяжки трицепсов. Например,' ||
                     ' отжимайтесь на брусьях, опускайте гантели за голову. Качаем пресс. Выполняем наклоны вперёд трижды' ||
                     ' по десять раз за подход. Тренируем ноги. Следует выполнять выпады вперёд с использованием гантелей.',4);
INSERT INTO fitness_program(short_name, duration, age_restriction, weight_restriction, sex_restriction, exercise_per_week,
                            description, category_id)
values('Дыхательная гимнастика', 19, 100, 110, 'm',2,'Упражнение 1. Исходное положение — стоя, ноги на ширине плеч. ' ||
                      'Медленно приподнимать пятки на 5 см от пола и резко их опускать (12-15 раз). Упражнение 2. ' ||
                      'Исходное положение — стоя, ноги на ширине плеч. Приподнять пятки от пола, переместив вес на носки ' ||
                      'и стоять в таком положении 3-4 секунды, затем плавно вес тела перенесите на пятки, приподняв носки,' ||
                      ' в таком положении стоять 2-3 секунды. Упражнение делать минуту. Упражнение 3. Ходьба на месте ' ||
                      '4-5 минут, при этом носки от пола не отрывать, а пятки немного приподнять. Упражнение 4. Исходное ' ||
                      'положение — стоя, правую ногу поднять, согнув в колене, образовав прямой угол. Вращать стопой ' ||
                      'вначале вовнутрь, потом наружу по 30 секунд в каждом направлении. Тоже самое повторить левой ' ||
                      'ногой. Делать упражнение в 3 подхода. Упражнение 5. Исходное положение — стоя, приподнять сначала ' ||
                      'правую ногу и слегка потрясти ею (4 секунды), затем левую. Сделать 4 раза каждой ногой. После ' ||
                      'данной группы упражнений рекомендуется контрастный душ для ног в течение пяти минут на каждую.',3);
INSERT INTO fitness_program(short_name, duration, age_restriction, weight_restriction, sex_restriction, exercise_per_week,
                            description, category_id)
values('Дыхательная гимнастика: велосипед', 20, 70, 90, 'f',2,'Велосипед. Количество: 25 раз. Техника выполнения: ' ||
                       'Лягте на спину, разместите руки вдоль туловища или уберите их за голову. Поднесите левое колено ' ||
                       'к правому локтю. Затем правое колено к левому локтю. Вначале стоит ' ||
                       'делать упражнение медленно, затем можно увеличить скорость. Удары на месте. Количество: 40 раз. ' ||
                       'Техника выполнения: Примите боксёрскую позицию, чуть согните колени. Разместите руки напротив ' ||
                       'лица. Перенесите вес на правую ногу и поочерёдно попробуйте нанести удары (правой и левой рукой). ' ||
                       'Смотреть на официальном канале в YouTube. Боковые прыжки. Количество: 30 раз/.Техника выполнения:' ||
                       'Поставьте ноги вместе. Начните прыгать из стороны в сторону. С каждым днём увеличивайте скорость ' ||
                       'и высоту прыжков. Смотреть на официальном канале в YouTube. Отжимания. Количество: 20 раз. ',3);
INSERT INTO fitness_program(short_name, duration, age_restriction, weight_restriction, sex_restriction, exercise_per_week,
                            description, category_id)
values('Фитнесс для начинающих', 18, 50, 120, 'm',4,'День 1-ый. Неспешная 30-минутная пробежка или быстрая ходьба. День 2-й отдыхаем. ' ||
                    'День 3-ий. Быстрая ходьба около 10 минут, далее – простые упражнения с минутными перерывами ' ||
                    'между подходами: 10 отжиманий от пола или 2 минуты в позе планки, 10 выпадов для каждой ноги с ' ||
                    'тремя подходами, 20 упражнений на пресс; 20 приседаний, 20 глубоких наклонов в сторону для каждой ' ||
                    'руки. День 4-ый отдыхаем. День 5-ый посвящаем кардиотренировкам. Программа та же, что в 1-ый день.' ||
                    ' День 6-ой – день отдыха. День 7-ой – бег в течение 30 минут или часовая прогулка. И повторяем ' ||
                    'все по кругу',2);
INSERT INTO fitness_program(short_name, duration, age_restriction, weight_restriction, sex_restriction, exercise_per_week,
                            description, category_id)
values('Фитнесс для начинающих', 22, 40, 120, 'f',3,'1. Скручивания на пресс. Прорабатывает мышцы пресса. Лягте на ' ||
                    'спину, поставьте стопы на пол, руки уберите за голову. Поднимайте корпус, чтобы лопатки отрывались' ||
                    ' от пола, а поясница оставалась прижатой. Не давите руками на голову, пальцы только касаются ' ||
                    'затылка, движение совершается за счёт напряжения мышц пресса, а не шеи. Выполните 3 подхода по 15–20 ' ||
                    'раз. 2. Гиперэкстензия. Прокачивает разгибатели спины, ягодицы и заднюю поверхность бедра. Вставьте ' ||
                    'ноги в тренажёр для гиперэкстензии, уберите руки за голову. Сохраняя прямую спину, опустите корпус,' ||
                    ' а затем поднимите его. В верхней точке смотрите в стену перед собой. Избегайте рывков и резких ' ||
                    'движений, выполняйте упражнение плавно и под контролем. Сделайте 3 подхода по 15 раз. В дальнейшем' ||
                    ' можете увеличить количество повторений до 20–25. 3. Приседания со штангой на спине. Нагружает ' ||
                    'бёдра, ягодицы и мышцы кора. Поставьте ноги чуть шире расправленных плеч, сведите лопатки, носки' ||
                    ' стоп немного разверните в стороны. Отведите таз назад, слегка прогнитесь в пояснице и со вдохом' ||
                    ' уйдите в присед. Держите спину прямой, смотрите вперёд. Присядьте до параллели бёдер с полом. ' ||
                    'Если пятки не отрываются от него, колени не заворачиваются внутрь, а спина остаётся прямой, ' ||
                    'попробуйте присесть ниже. Если при этом спина округлилась, возвращайтесь к предыдущему положению, ' ||
                    'то есть снова делайте бёдра параллельными полу. Выходите из приседания на выдохе. Начинайте с ' ||
                    'грифом на 15 или 20 кг и постепенно повышайте нагрузку. Всё время следите за техникой. 4. Жим лёжа.' ||
                    ' Прокачивает грудные мышцы и трицепсы. Лягте на лавку для жима, прижмите стопы к полу. Прямым ' ||
                    'хватом шире плеч возьмитесь за штангу. Снимите её со стоек, опустите до касания груди и выжмите ' ||
                    'обратно. 5. Разведение гантелей стоя. Укрепляет плечи. Встаньте прямо, поднимите руки с гантелями ' ||
                    'в стороны до уровня плеч и опустите обратно. Оставляйте локти чуть согнутыми, чтобы не перегрузить' ||
                    '0 сустав.',4);

INSERT INTO users (first_name, last_name, date_of_birth, sex, weight, email)
values ('Petr', 'Ivanov', '1980-03-12','m',86,'petrIvanov@mail.ru');
INSERT INTO users (first_name, last_name, date_of_birth, sex, weight, email)
values ('Olya', 'Mak', '1985-04-22','f',55,'Busnab@mail.ru');
INSERT INTO users (first_name, last_name, date_of_birth, sex, weight, email)
values ('Andrey', 'Andreev', '1993-12-24','m',80,'Busnab@mail.ru');

INSERT INTO bookmark (fitness_program_id, user_id) values (1,3);
INSERT INTO bookmark (fitness_program_id, user_id) values (3,3);
INSERT INTO bookmark (fitness_program_id, user_id) values (2,2);
INSERT INTO bookmark (fitness_program_id, user_id) values (7,2);

INSERT INTO active_program (days, is_complited, fitness_program_id, user_id) VALUES ('MONDAY;FRIDAY;SUNDAY',true,6,3);
INSERT INTO active_program (days, is_complited, fitness_program_id, user_id) VALUES ('MONDAY;SUNDAY',true,3,3);
INSERT INTO active_program (days, is_complited, fitness_program_id, user_id) VALUES ('MONDAY;SATURDAY',false,1,3);
INSERT INTO active_program (days, is_complited, fitness_program_id, user_id) VALUES ('MONDAY;FRIDAY;SUNDAY',true,4,2);
INSERT INTO active_program (days, is_complited, fitness_program_id, user_id) VALUES ('MONDAY;SATURDAY',false,1,2);

INSERT INTO review (author_id, author_name, text, fitness_program_id)
VALUES (3,'Petr' ,'Отличная программа упражнений. Рекомендую!',6);
INSERT INTO review (author_id, author_name, text, fitness_program_id)
VALUES (3,'Petr' ,'Мне понравилось', 4);
INSERT INTO review (author_id, author_name, text, fitness_program_id)
VALUES (2,'Olya','Программа подойдет для всех',4);

INSERT INTO exercise_schedule(program_short_name, exercise_date, is_complited, active_program_id)
values ('руки и пресс','2021-11-17',false,1);
INSERT INTO exercise_schedule(program_short_name, exercise_date, is_complited, active_program_id)
values ('руки и пресс','2021-11-17',false,5);