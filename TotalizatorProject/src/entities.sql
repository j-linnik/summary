create table totalizator_user                   -- пользователь
(
    user_mail varchar (100) not null,
    user_balance integer not null,
    primary key(user_mail)
);

create table totalizator_horserace              -- гонка(забег)
(
    horserace_id SERIAL,
    horserace_date timestamp not null,
    horserace_winner varchar(30) not null,
    primary key(horserace_id)
);

create table totalizator_bet                    -- ставка
(
    bet_id SERIAL,
    user_mail varchar(100) not null,
    race_id integer not null,
    bet_horse varchar(30) not null,
    bet_summ integer not null,
    primary key(bet_id)
);

create table horserace_horses                   -- таблица-список лошадей-участников забегов
(
    horserace_id integer not null, 
    horse varchar(30) not null
)