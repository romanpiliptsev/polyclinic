CREATE TABLE IF NOT EXISTS patient
(
    patientId   INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    firstName   varchar(255) NOT NULL,
    lastName      varchar(255) NOT NULL,
    patronymic   varchar(255) NOT NULL,
    policy  varchar(16) NOT NULL,
    phoneNumber varchar(15)  NOT NULL
);

CREATE TABLE IF NOT EXISTS doctor
(
    doctorId      INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    firstName     varchar(255) NOT NULL,
    lastName        varchar(255) NOT NULL,
    patronymic     varchar(255) NOT NULL,
    specialization varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS recipe_priority
(
    priorityId   INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    priorityName varchar(63) NOT NULL
);

CREATE TABLE IF NOT EXISTS recipe
(
    recipeId     INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    description   varchar(255) NOT NULL,
    creationDate DATE DEFAULT (CURRENT_DATE),
    validity    INT NOT NULL,
    patientId INT NOT NULL,
    doctorId INT NOT NULL,
    priorityId INT NOT NULL,
    FOREIGN KEY (patientId) REFERENCES patient(patientId),
    FOREIGN KEY (doctorId) REFERENCES doctor(doctorId),
    FOREIGN KEY (priorityId) REFERENCES recipe_priority(priorityId)
);

CREATE TABLE IF NOT EXISTS appointment
(
    appointmentId     INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    description   varchar(255) NOT NULL,
    patientId INT NOT NULL,
    doctorId INT NOT NULL,
    FOREIGN KEY (patientId) REFERENCES patient(patientId),
    FOREIGN KEY (doctorId) REFERENCES doctor(doctorId),
    appointmentDate DATE DEFAULT (CURRENT_DATE)
);