package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static util.DatabaseOps.performQuery;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import model.Student;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class TestStudentPersistence {
  private static final PostgreSQLContainer databaseContainer =
      new PostgreSQLContainer(DockerImageName.parse("postgres:alpine3.14"));
  private StudentPersistence studentPersistence;

  @BeforeAll
  static void setUp() {
    databaseContainer.start();
  }

  @BeforeEach
  void initialize() throws Throwable {
    databaseContainer.start();
    String parentPath = Path.of(System.getProperty("user.dir")).getParent().toString();
    Path path =
        Path.of(parentPath + "/backend/src/main/resources/queries/database_initialization.sql");
    performQuery(databaseContainer, Files.readString(path));
    studentPersistence =
        new StudentPersistence(
            databaseContainer.getJdbcUrl(),
            databaseContainer.getUsername(),
            databaseContainer.getPassword(),
            1);
    studentPersistence.add(new Student(1L, "Adam", "Kowalski", 1));
    studentPersistence.add(new Student(2L, "Adam", "Niewiadomski", 1));
  }

  @AfterEach
  public void tearDownEach() {
    databaseContainer.stop();
  }

  @Test
  public void testGetAll() {
    List<Student> students = studentPersistence.getAll();
    assertEquals(students.size(), 2);
    assertEquals(students.get(0).getFirstName(), "Adam");
    assertEquals(students.get(0).getLastName(), "Kowalski");
    assertEquals(students.get(0).getClassNumber(), 1);
    assertEquals(students.get(1).getFirstName(), "Adam");
    assertEquals(students.get(1).getLastName(), "Niewiadomski");
    assertEquals(students.get(1).getClassNumber(), 1);
  }
}
