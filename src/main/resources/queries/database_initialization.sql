DROP TABLE IF EXISTS STUDENT CASCADE;
DROP TABLE IF EXISTS TEACHER CASCADE;
DROP TABLE IF EXISTS SUBJECT CASCADE;
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

CREATE TABLE subject
  (
     id              SERIAL PRIMARY KEY,
     name            VARCHAR,
     lesson_length   INTEGER,
     class_range     INTEGER [],
     is_itn          bool,
     is_mandatory    bool
  );

CREATE TABLE lesson
(
    student_id      INTEGER,
    teacher_id      VARCHAR,
    subject_id      INTEGER,
    PRIMARY KEY (student_id, teacher_id, subject_id),
    CONSTRAINT fk_student
        FOREIGN KEY(student_id)
            REFERENCES student(id),
    CONSTRAINT fk_teacher
        FOREIGN KEY(teacher_id)
            REFERENCES teacher(firebase_id),
    CONSTRAINT fk_subject
        FOREIGN KEY(subject_id)
            REFERENCES subject(id)
);

CREATE TABLE known_subject
(
    teacher_id      VARCHAR,
    subject_id      INTEGER,
    PRIMARY KEY (teacher_id, subject_id),
    CONSTRAINT fk_teacher
        FOREIGN KEY(teacher_id)
            REFERENCES teacher(firebase_id),
    CONSTRAINT fk_subject
        FOREIGN KEY(subject_id)
            REFERENCES subject(id)
);
