package persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.nio.file.Files;
import java.nio.file.Path;

import static util.DatabaseOps.performQuery;

public class TestStudentPersistence {
  private static PostgreSQLContainer databaseContainer;
  private static final HikariConfig config = new HikariConfig();
  private static Jdbi jdbi = null;
  private static StudentPersistence studentPersistence = null;

  @BeforeAll
  static void setUp() {
    databaseContainer.start();
    final String port = databaseContainer.getFirstMappedPort().toString();
    String url = "jdbc:postgresql://localhost:" + port + "/postgres";
    config.setJdbcUrl(url);
    config.setUsername("sa");
    config.setPassword("sa");
    DataSource dataSource = new HikariDataSource(config);
    jdbi = Jdbi.create(dataSource);
    studentPersistence = new StudentPersistence();
  }

  @BeforeEach
  void initialize() throws Throwable {
    databaseContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:alpine3.14"));
    databaseContainer.start();
    String parentPath = Path.of(System.getProperty("user.dir")).getParent().toString();
    Path path = Path.of(parentPath + "/persistence/src/main/resources/database_creation.sql");
    performQuery(databaseContainer, Files.readString(path));
    studentPersistence = new StudentPersistence(databaseContainer.getJdbcUrl(), databaseContainer.getUsername(), databaseContainer.getPassword(), 1);
  }


  @AfterAll
  public static void tearDown() {
    databaseContainer.stop();
  }

  @AfterEach
    public void tearDownEach() {
        jdbi.useHandle(handle -> handle.execute("DELETE FROM student"));
    }

  //  @Test
  //  public void testGetOne() {
  //    Student student = studentPersistence.getOne(1);
  //    assertEquals(student.getFirstName(), "Adam");
  //    assertEquals(student.getLastName(), "kowalski");
  //    assertEquals(student.getClassNumber(), 1);
  //  }
}
