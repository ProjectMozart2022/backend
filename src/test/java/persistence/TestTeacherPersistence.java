package persistence;

import static org.junit.jupiter.api.Assertions.*;
import static util.DatabaseOps.performQuery;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import model.Teacher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class TestTeacherPersistence {
  private static final PostgreSQLContainer databaseContainer =
      new PostgreSQLContainer(DockerImageName.parse("postgres:alpine3.14"));
  private TeacherPersistence teacherPersistence;
  private final Teacher teacherAdam =
      new Teacher("uuid", "Adam", "Kowalski", "adam@sosnowa.pl", "razDwaTrzy");
  private final Teacher teacherAndrzej =
      new Teacher("id", "Andrzej", "Kowalski", "andrzej@sosnowa.pl", "razDwaCztery");

  @BeforeEach
  void initialize() throws Throwable {
    databaseContainer.start();
    String parentPath = Path.of(System.getProperty("user.dir")).getParent().toString();
    Path path =
        Path.of(parentPath + "/backend/src/main/resources/queries/database_initialization.sql");
    performQuery(databaseContainer, Files.readString(path));
    teacherPersistence =
        new TeacherPersistence(
            databaseContainer.getJdbcUrl(),
            databaseContainer.getUsername(),
            databaseContainer.getPassword(),
            2);
    teacherPersistence.add(teacherAdam);
    teacherPersistence.add(teacherAndrzej);
  }

  @AfterEach
  public void tearDownEach() {
    databaseContainer.stop();
  }

  @Test
  public void testGetAll() {
    List<Teacher> teachers = teacherPersistence.getAll();
    assertEquals(teachers.size(), 2);
    assertEquals(teachers.get(0).getFirstName(), "Adam");
    assertEquals(teachers.get(0).getLastName(), "Kowalski");
    assertEquals(teachers.get(0).getEmail(), "adam@sosnowa.pl");
    assertEquals(teachers.get(0).getPassword(), "razDwaTrzy");
    assertEquals(teachers.get(1).getFirstName(), "Andrzej");
    assertEquals(teachers.get(1).getLastName(), "Kowalski");
    assertEquals(teachers.get(1).getEmail(), "andrzej@sosnowa.pl");
    assertEquals(teachers.get(1).getPassword(), "razDwaCztery");
  }

  @Test
  public void testGetOne() {
    Teacher teacher = teacherPersistence.getOne("uuid");
    assertEquals(teacher.getFirstName(), "Adam");
    assertEquals(teacher.getLastName(), "Kowalski");
    assertEquals(teacher.getEmail(), "adam@sosnowa.pl");
  }

  @Test
  public void testAdd() {
    Teacher teacher = new Teacher("janId", "Jan", "Kowalski", "jan@sosnowa.pl", "razDwaPięć");
    teacherPersistence.add(teacher);
    Teacher teacherFromDb = teacherPersistence.getOne("janId");
    assertNotNull(teacherFromDb);
    assertEquals(teacherFromDb.getFirstName(), teacher.getFirstName());
    assertEquals(teacherFromDb.getLastName(), teacher.getLastName());
    assertEquals(teacherFromDb.getEmail(), teacher.getEmail());
    assertEquals(teacherFromDb.getPassword(), teacher.getPassword());
  }

  @Test
  public void testUpdate() {
    Teacher teacher = new Teacher("uuid", "John", "Nowak", "xd@sosnowa.pl", "xd123");
    teacherPersistence.update(teacher);
    Teacher teacherFromDb = teacherPersistence.getOne("uuid");
    assertNotNull(teacherFromDb);
    assertEquals(teacherFromDb.getFirebaseId(), teacher.getFirebaseId());
    assertEquals(teacherFromDb.getFirstName(), teacher.getFirstName());
    assertEquals(teacherFromDb.getLastName(), teacher.getLastName());
    assertEquals(teacherFromDb.getEmail(), teacher.getEmail());
    assertEquals(teacherFromDb.getPassword(), teacher.getPassword());
  }

  @Test
  public void testDelete() {
    teacherPersistence.delete("uuid");
    List<Teacher> teachers = teacherPersistence.getAll();
    assertEquals(teachers.size(), 1);
  }
}
