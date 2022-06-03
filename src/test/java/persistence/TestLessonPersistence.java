package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static util.DatabaseOps.performQuery;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import model.Lesson;
import model.Student;
import model.Subject;
import model.Teacher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class TestLessonPersistence {
  private static final PostgreSQLContainer databaseContainer =
      new PostgreSQLContainer(DockerImageName.parse("postgres:alpine3.14"));
  private LessonPersistence lessonPersistence;
  private final Subject subjectPiano = new Subject(1L, "Piano", 60, List.of(1, 6), false);
  private final Subject subjectGuitar = new Subject(2L, "Guitar", 45, List.of(1, 6), false);
  private final Student studentAdam = new Student(1, "Adam", "Kowalski", 1);
  private final Student studentJohn = new Student(2, "John", "Doe", 1);
  private final Teacher teacherAdam =
      new Teacher("uuid", "Adam", "Kowalski", "adam@sosnowa.pl", "razDwaTrzy");
  private final Teacher teacherAndrzej =
      new Teacher("id", "Andrzej", "Kowalski", "andrzej@sosnowa.pl", "razDwaCztery");

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
    lessonPersistence =
        new LessonPersistence(
            databaseContainer.getJdbcUrl(),
            databaseContainer.getUsername(),
            databaseContainer.getPassword(),
            2);
    StudentPersistence studentPersistence =
        new StudentPersistence(
            databaseContainer.getJdbcUrl(),
            databaseContainer.getUsername(),
            databaseContainer.getPassword(),
            2);
    TeacherPersistence teacherPersistence =
        new TeacherPersistence(
            databaseContainer.getJdbcUrl(),
            databaseContainer.getUsername(),
            databaseContainer.getPassword(),
            2);
    SubjectPersistence subjectPersistence =
        new SubjectPersistence(
            databaseContainer.getJdbcUrl(),
            databaseContainer.getUsername(),
            databaseContainer.getPassword(),
            2);
    studentPersistence.add(studentAdam);
    studentPersistence.add(studentJohn);
    teacherPersistence.add(teacherAdam);
    teacherPersistence.add(teacherAndrzej);
    subjectPersistence.add(subjectPiano);
    subjectPersistence.add(subjectGuitar);
    lessonPersistence.add(studentAdam.getId(), teacherAdam.getFirebaseId(), subjectPiano.getId());
    lessonPersistence.add(
        studentAdam.getId(), teacherAndrzej.getFirebaseId(), subjectGuitar.getId());
  }

  @AfterEach
  public void tearDownEach() {
    databaseContainer.stop();
  }

  @Test
  public void testGetAllByStudent() {
    List<Lesson> subjects = lessonPersistence.getAllByStudent(studentAdam.getId());
    assertEquals(2, subjects.size());
  }

  @Test
  public void testGetAllByTeacher() {
    List<Lesson> subjects = lessonPersistence.getAllByTeacher(teacherAdam.getFirebaseId());
    assertEquals(1, subjects.size());
  }
}
