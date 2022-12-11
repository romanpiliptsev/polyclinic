CREATE TABLE IF NOT EXISTS patient
(
    patientId   bigint IDENTITY PRIMARY KEY,
    firstName   varchar(255) NOT NULL,
    lastName      varchar(255) NOT NULL,
    patronymic   varchar(255) NOT NULL,
    policy  varchar(16) NOT NULL,
    phoneNumber varchar(15)  NOT NULL
);

CREATE TABLE IF NOT EXISTS doctor
(
    doctorId      bigint IDENTITY PRIMARY KEY,
    firstName     varchar(255) NOT NULL,
    lastName        varchar(255) NOT NULL,
    patronymic     varchar(255) NOT NULL,
    specialization varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS recipePriority
(
    priorityId   bigint IDENTITY PRIMARY KEY,
    priorityName varchar(63) NOT NULL
);

CREATE TABLE IF NOT EXISTS recipe
(
    recipeId     bigint IDENTITY PRIMARY KEY,
    description   varchar(255) NOT NULL,
    patientId    bigint FOREIGN KEY REFERENCES patient(patientId),
    doctorId     bigint FOREIGN KEY REFERENCES doctor(doctorId),
    creationDate date DEFAULT current_date NOT NULL,
    validity    int NOT NULL,
    priorityId   bigint FOREIGN KEY REFERENCES recipePriority(priorityId)
);

CREATE TABLE IF NOT EXISTS appointment
(
    appointmentId     bigint IDENTITY PRIMARY KEY,
    description   varchar(255) NOT NULL,
    patientId    bigint FOREIGN KEY REFERENCES patient(patientId),
    doctorId     bigint FOREIGN KEY REFERENCES doctor(doctorId),
    appointmentDate date DEFAULT current_date NOT NULL
);