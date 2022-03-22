DROP TABLE IF EXISTS STUDENT CASCADE;
DROP TABLE IF EXISTS TEACHER CASCADE;
DROP TABLE IF EXISTS PROFILE CASCADE;

CREATE TABLE student
  (
     id              SERIAL PRIMARY KEY,
     first_name      VARCHAR,
     last_name       VARCHAR,
     class_number    INTEGER,
     is_present      bool
  );

CREATE TABLE teacher
  (
     id              SERIAL PRIMARY KEY,
     first_name      VARCHAR,
     last_name       VARCHAR,
     is_present      bool
  );

CREATE TABLE profile
  (
     id              SERIAL PRIMARY KEY,
     name            VARCHAR,
     lesson_length   INTEGER,
     class_range     VARCHAR,
     is_itn          bool,
     is_present      bool
  );

