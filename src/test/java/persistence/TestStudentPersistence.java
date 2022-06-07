package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import model.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import util.DatabaseOps;

public class TestStudentPersistence {
  private static final PostgreSQLContainer databaseContainer =
      new PostgreSQLContainer(DockerImageName.parse("postgres:alpine3.14"));
  private StudentPersistence studentPersistence;
  private final Student studentAdam = new Student(1, "Adam", "Kowalski", 1);
  private final Student studentJohn = new Student(2, "John", "Doe", 1);

  @BeforeEach
  void initialize() {
    DatabaseOps.initializeContainerWithEmptyDatabase(databaseContainer);
    studentPersistence =
        new StudentPersistence(
            databaseContainer.getJdbcUrl(),
            databaseContainer.getUsername(),
            databaseContainer.getPassword(),
            2);
    studentPersistence.add(studentAdam);
    studentPersistence.add(studentJohn);
  }

  @AfterEach
  public void tearDownEach() {
    databaseContainer.stop();
  }

  @Test
  public void shouldGetAllStudentsInDb() {
    List<Student> students = studentPersistence.getAll();
    assertEquals(students.size(), 2);
    assertEquals(students.get(0).getFirstName(), "Adam");
    assertEquals(students.get(0).getLastName(), "Kowalski");
    assertEquals(students.get(0).getClassNumber(), 1);
    assertEquals(students.get(1).getFirstName(), "John");
    assertEquals(students.get(1).getLastName(), "Doe");
    assertEquals(students.get(1).getClassNumber(), 1);
  }

  @Test
  public void shouldAddNewStudent() {
    List<Student> students = studentPersistence.getAll();
    assertEquals(students.size(), 2);
    studentPersistence.add(new Student(3L, "Adam", "Nowak", 1));
    students = studentPersistence.getAll();
    assertEquals(students.size(), 3);
    assertEquals(students.get(2).getFirstName(), "Adam");
    assertEquals(students.get(2).getLastName(), "Nowak");
    assertEquals(students.get(2).getClassNumber(), 1);
  }

  @Test
  public void shouldUpdateStudent() {
    List<Student> students = studentPersistence.getAll();
    assertEquals(students.size(), 2);
    studentPersistence.update(new Student(1, "Jędrzej", "Andrzejczak", 2));
    List<Student> updatedStudents = studentPersistence.getAll();
    assertEquals(updatedStudents.size(), 2);
    assertEquals(updatedStudents.get(1).getFirstName(), "Jędrzej");
    assertEquals(updatedStudents.get(1).getLastName(), "Andrzejczak");
    assertEquals(updatedStudents.get(1).getClassNumber(), 2);
  }

  @Test
  public void shouldDeleteStudent() {
    List<Student> students = studentPersistence.getAll();
    assertEquals(students.size(), 2);
    studentPersistence.delete(1);
    students = studentPersistence.getAll();
    assertEquals(students.size(), 1);
    assertEquals(students.get(0).getFirstName(), "John");
    assertEquals(students.get(0).getLastName(), "Doe");
    assertEquals(students.get(0).getClassNumber(), 1);
  }
}
