DROP TABLE IF EXISTS ACCOUNT CASCADE;
DROP TABLE IF EXISTS STUDENT CASCADE;
DROP TABLE IF EXISTS TEACHER CASCADE;
DROP TABLE IF EXISTS PROFILE CASCADE;


CREATE TABLE account
  (
     id              SERIAL PRIMARY KEY,
     email           VARCHAR NOT NULL,
     password        VARCHAR NOT NULL,
     role            VARCHAR NOT NULL.
     firebase_uid    VARCHAR NOT NULL
  );

CREATE TABLE student
  (
     id              SERIAL PRIMARY KEY,
     first_name      VARCHAR,
     last_name       VARCHAR,
     class_number    INTEGER
  );

CREATE TABLE teacher
  (
     id              SERIAL PRIMARY KEY,
     first_name      VARCHAR,
     last_name       VARCHAR,
     account_id 	 INT NOT NULL,
     CONSTRAINT fk_user
        FOREIGN KEY(account_id)
            REFERENCES account(id)
  );

CREATE TABLE profile
  (
     id              SERIAL PRIMARY KEY,
     name            VARCHAR,
     lesson_length   INTEGER,
     class_range     INTEGER [],
     is_itn          bool
  );