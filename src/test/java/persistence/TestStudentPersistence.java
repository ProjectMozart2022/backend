package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static util.DatabaseOps.performQuery;

import com.zaxxer.hikari.HikariConfig;
import java.nio.file.Files;
import java.nio.file.Path;
import model.Student;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class TestStudentPersistence {
  private static final PostgreSQLContainer databaseContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:alpine3.14")).;
  private static final HikariConfig config = new HikariConfig();
  private static StudentPersistence studentPersistence = null;

  @BeforeAll
  static void setUp() {
    databaseContainer.start();
    final String port = databaseContainer.getFirstMappedPort().toString();
    String url = "jdbc:postgresql://localhost:" + port + "/postgres";
  }

  @BeforeEach
  void initialize() throws Throwable {
    databaseContainer.start();
    String parentPath = Path.of(System.getProperty("user.dir")).getParent().toString();
    Path path = Path.of(parentPath + "/src/java/resources/queries/database_initialization.sql");
    performQuery(databaseContainer, Files.readString(path));
    studentPersistence =
        new StudentPersistence(
            databaseContainer.getJdbcUrl(),
            databaseContainer.getUsername(),
            databaseContainer.getPassword(),
            1);
  }

  @AfterEach
  public void tearDownEach() {
    databaseContainer.stop();
  }

  @Test
  public void testGetOne() {
    Student student = studentPersistence.getOne(1);
    assertEquals(student.getFirstName(), "Adam");
    assertEquals(student.getLastName(), "kowalski");
    assertEquals(student.getClassNumber(), 1);
  }
}
