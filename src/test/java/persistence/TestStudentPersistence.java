package persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.PostgreSQLContainer;

public class TestStudentPersistence {
  private static final PostgreSQLContainer databaseContainer =
      new PostgreSQLContainer("postgres:11.1")
          .withDatabaseName("postgres")
          .withUsername("sa")
          .withPassword("sa");
  private final HikariConfig config = new HikariConfig();
  private Jdbi jdbi = null;
  private StudentPersistence studentPersistence = null;

  @BeforeEach
  public void setUp() {
    databaseContainer.start();
    final String port = databaseContainer.getFirstMappedPort().toString();
    String url = "jdbc:postgresql://localhost:" + port + "/postgres";
    config.setJdbcUrl(url);
    config.setUsername("sa");
    config.setPassword("sa");
    DataSource dataSource = new HikariDataSource(config);
    jdbi = Jdbi.create(dataSource);
    studentPersistence = new StudentPersistence(jdbi);
    jdbi.inTransaction(
        handle ->
            handle.execute(
                "DROP TABLE IF EXISTS STUDENT CASCADE;\n"
                    + "DROP TABLE IF EXISTS TEACHER CASCADE;\n"
                    + "DROP TABLE IF EXISTS SUBJECT CASCADE;\n"
                    + "DROP TABLE IF EXISTS LESSON CASCADE;\n"
                    + "\n"
                    + "CREATE TABLE student\n"
                    + "  (\n"
                    + "     id              SERIAL PRIMARY KEY,\n"
                    + "     first_name      VARCHAR,\n"
                    + "     last_name       VARCHAR,\n"
                    + "     class_number    INTEGER\n"
                    + "  );\n"
                    + "\n"
                    + "CREATE TABLE teacher\n"
                    + "  (\n"
                    + "     firebase_id     VARCHAR PRIMARY KEY,\n"
                    + "     first_name      VARCHAR,\n"
                    + "     last_name       VARCHAR,\n"
                    + "     email           VARCHAR,\n"
                    + "     password        VARCHAR\n"
                    + "  );\n"
                    + "\n"
                    + "CREATE TABLE subject\n"
                    + "  (\n"
                    + "     id              SERIAL PRIMARY KEY,\n"
                    + "     name            VARCHAR,\n"
                    + "     lesson_length   INTEGER,\n"
                    + "     class_range     INTEGER [],\n"
                    + "     is_itn          bool\n"
                    + "  );\n"
                    + "\n"
                    + "CREATE TABLE lesson\n"
                    + "(\n"
                    + "    student_id      INTEGER,\n"
                    + "    teacher_id      VARCHAR,\n"
                    + "    subject_id      INTEGER,\n"
                    + "    PRIMARY KEY (student_id, teacher_id, subject_id),\n"
                    + "    CONSTRAINT fk_student\n"
                    + "        FOREIGN KEY(student_id)\n"
                    + "            REFERENCES student(id),\n"
                    + "    CONSTRAINT fk_teacher\n"
                    + "        FOREIGN KEY(teacher_id)\n"
                    + "            REFERENCES teacher(firebase_id),\n"
                    + "    CONSTRAINT fk_subject\n"
                    + "        FOREIGN KEY(subject_id)\n"
                    + "            REFERENCES subject(id)\n"
                    + ");\n"));
    jdbi.inTransaction(
        handle ->
            handle.execute(
                "INSERT INTO "
                    + "student (first_name, last_name, class_number) VALUES ('Adam', 'kowalski', 1)"));
  }

  @AfterAll
  public static void tearDown() {
    databaseContainer.stop();
  }

  //  @Test
  //  public void testGetOne() {
  //    Student student = studentPersistence.getOne(1);
  //    assertEquals(student.getFirstName(), "Adam");
  //    assertEquals(student.getLastName(), "kowalski");
  //    assertEquals(student.getClassNumber(), 1);
  //  }
}
