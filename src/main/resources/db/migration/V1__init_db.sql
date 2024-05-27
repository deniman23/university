CREATE TABLE "students"
(
    first_name  varchar(255) NULL,
    last_name   varchar(255) NULL,
    middle_name varchar(255) NULL,
    sex         varchar(255) NULL,
    id          uuid         NOT NULL,
    group_id    uuid         NULL,
    gender      varchar(255) NULL,
    CONSTRAINT students_pk PRIMARY KEY (id)

);

CREATE TABLE "groups"
(
    course     int4         NULL,
    name       varchar(255) NULL,
    curator_id uuid         NULL,
    monitor_id uuid         NULL,
    id         uuid         NOT NULL,
    CONSTRAINT groups_pk PRIMARY KEY (id),
    CONSTRAINT uk_monitor UNIQUE (monitor_id),
    CONSTRAINT uk_curator UNIQUE (curator_id)

);

CREATE TABLE "lessons"
(
    date_time  timestamp(6) NULL,
    "name"     varchar(255) NULL,
    id         uuid         NOT NULL,
    group_id   uuid         NULL,
    subject_id uuid         NULL,
    teacher_id uuid         NULL,
    CONSTRAINT lessons_pk PRIMARY KEY (id)

);

CREATE TABLE "subjects"
(
    id         uuid         NOT NULL,
    title      varchar(255) NULL,
    teacher_id uuid         NULL,
    CONSTRAINT subjects_pkey PRIMARY KEY (id)
);

CREATE TABLE "teacher_subjects"
(
    subject_id uuid NULL,
    teacher_id uuid NULL,
    id         uuid NOT NULL,
    CONSTRAINT teacher_subjects_pkey PRIMARY KEY (id)
);

CREATE TABLE "teachers"
(
    first_name  varchar(255) NULL,
    last_name   varchar(255) NULL,
    middle_name varchar(255) NULL,
    id          uuid         NOT NULL,
    gender      varchar(255) NULL,
    CONSTRAINT teachers_pk PRIMARY KEY (id)
);




