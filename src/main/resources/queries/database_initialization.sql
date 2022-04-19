DROP TABLE IF EXISTS STUDENT CASCADE;
DROP TABLE IF EXISTS TEACHER CASCADE;
DROP TABLE IF EXISTS PROFILE CASCADE;
DROP TABLE IF EXISTS LESSON CASCADE;

CREATE TABLE student
  (
     id              SERIAL PRIMARY KEY,
     first_name      VARCHAR,
     last_name       VARCHAR,
     class_number    INTEGER
  );

CREATE TABLE teacher
  (
     firebase_id     VARCHAR PRIMARY KEY,
     first_name      VARCHAR,
     last_name       VARCHAR,
     email           VARCHAR,
     password        VARCHAR
  );

CREATE TABLE profile
  (
     id              SERIAL PRIMARY KEY,
     name            VARCHAR,
     lesson_length   INTEGER,
     class_range     INTEGER [],
     is_itn          bool
  );

CREATE TABLE lesson
(
    student_id      INTEGER,
    teacher_id      VARCHAR,
    profile_id      INTEGER,
    PRIMARY KEY (student_id, teacher_id, profile_id),
    CONSTRAINT fk_student
        FOREIGN KEY(student_id)
            REFERENCES student(id),
    CONSTRAINT fk_teacher
        FOREIGN KEY(teacher_id)
            REFERENCES teacher(firebase_id),
    CONSTRAINT fk_profile
        FOREIGN KEY(profile_id)
            REFERENCES profile(id)
);
