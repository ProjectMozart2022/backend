package persistence;

import model.Teacher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import util.DatabaseOps;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestTeacherPersistence {
  private static final PostgreSQLContainer databaseContainer =
      new PostgreSQLContainer(DockerImageName.parse("postgres:alpine3.14"));
  private TeacherPersistence teacherPersistence;
  private final Teacher teacherAdam =
      new Teacher("uuid", "Adam", "Kowalski", "adam@sosnowa.pl", "razDwaTrzy");
  private final Teacher teacherAndrzej =
      new Teacher("id", "Andrzej", "Kowalski", "andrzej@sosnowa.pl", "razDwaCztery");

  @BeforeEach
  void initialize() {
    DatabaseOps.initializeContainerWithEmptyDatabase(databaseContainer);
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
  public void shouldGetAllTeachers() {
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
  public void shouldGetTeacherWithGivenId() {
    Teacher teacher = teacherPersistence.getOne("uuid");
    assertEquals(teacher.getFirstName(), "Adam");
    assertEquals(teacher.getLastName(), "Kowalski");
    assertEquals(teacher.getEmail(), "adam@sosnowa.pl");
  }

  @Test
  public void shouldAddNewTeacher() {
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
  public void shouldUpdateTeacher() {
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
  public void shouldDeleteTeacher() {
    teacherPersistence.delete("uuid");
    List<Teacher> teachers = teacherPersistence.getAll();
    assertEquals(teachers.size(), 1);
  }
}
