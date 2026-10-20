CREATE TABLE IF NOT EXISTS STUDENTS (
    id int NOT NULL AUTO_INCREMENT,
    name varchar(15),
    surname varchar(25),
    faculty varchar(20),
    age int,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS DISCIPLINES (
    id int NOT NULL AUTO_INCREMENT,
    name varchar(100) NOT NULL,
    teacher varchar(60),
    hours int,
    semester int,
    PRIMARY KEY (id)
);
