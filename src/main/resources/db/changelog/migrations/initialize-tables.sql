SET search_path TO ${database.defaultSchemaName};

CREATE TABLE sensor_types
(
    id   SERIAL PRIMARY KEY,
    name TEXT NOT NULL unique
);

CREATE TABLE sensor_units
(
    id   SERIAL PRIMARY KEY,
    name TEXT NOT NULL unique
);

CREATE TABLE sensors
(
    id          SERIAL PRIMARY KEY,
    name        TEXT    NOT NULL,
    model       TEXT    NOT NULL,
    range_from  INTEGER NOT NULL,
    range_to    INTEGER NOT NULL,
    type_id     INTEGER NOT NULL REFERENCES sensor_types (id),
    unit_id     INTEGER REFERENCES sensor_units (id),
    location    TEXT,
    description TEXT
);

INSERT INTO sensor_types (name)
VALUES ('Pressure'),
       ('Voltage'),
       ('Temperature'),
       ('Humidity');

INSERT INTO sensor_units (name)
VALUES ('bar'),
       ('voltage'),
       ('°С'),
       ('%');


