package persistence;

import model.Subject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import util.DatabaseOps;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestSubjectPersistence {
  private static final PostgreSQLContainer databaseContainer =
      new PostgreSQLContainer(DockerImageName.parse("postgres:alpine3.14"));
  private SubjectPersistence subjectPersistence = null;
  private final Subject subjectPiano = new Subject(1L, "Piano", 60, List.of(1, 6), false);
  private final Subject subjectGuitar = new Subject(2L, "Guitar", 45, List.of(1, 6), false);

  @BeforeEach
  void initialize() {
    DatabaseOps.initializeContainerWithEmptyDatabase(databaseContainer);
    subjectPersistence =
        new SubjectPersistence(
            databaseContainer.getJdbcUrl(),
            databaseContainer.getUsername(),
            databaseContainer.getPassword(),
            2);
    subjectPersistence.add(subjectPiano);
    subjectPersistence.add(subjectGuitar);
  }

  @AfterEach
  public void tearDownEach() {
    databaseContainer.stop();
  }

  @Test
  public void shouldGetAllSubjects() {
    List<Subject> subjects = subjectPersistence.getAll();
    assertEquals(subjects.size(), 2);
    assertEquals(subjects.get(0).getName(), "Piano");
    assertEquals(subjects.get(0).getLessonLength(), 60);
    assertEquals(subjects.get(0).getClassRange(), List.of(1, 6));
    assertFalse(subjects.get(0).isItn());
    assertEquals(subjects.get(1).getName(), "Guitar");
    assertEquals(subjects.get(1).getLessonLength(), 45);
    assertEquals(subjects.get(1).getClassRange(), List.of(1, 6));
    assertFalse(subjects.get(1).isItn());
  }

  @Test
  public void shouldAddNewSubject() {
    List<Subject> subjects = subjectPersistence.getAll();
    assertEquals(subjects.size(), 2);
    Long newId = subjectPersistence.add(new Subject(3L, "Violin", 60, List.of(1, 6), false));
    assertEquals(newId, 3L);
    subjects = subjectPersistence.getAll();
    assertEquals(subjects.get(2).getName(), "Violin");
    assertEquals(subjects.get(2).getLessonLength(), 60);
    assertEquals(subjects.get(2).getClassRange(), List.of(1, 6));
    assertFalse(subjects.get(2).isItn());
  }

  @Test
  public void shouldUpdateSubject() {
    List<Subject> subjects = subjectPersistence.getAll();
    assertEquals(subjects.size(), 2);
    subjectGuitar.setItn(true);
    subjectGuitar.setClassRange(List.of(1, 7));
    subjectPersistence.update(subjectGuitar);
    subjects = subjectPersistence.getAll();
    assertEquals(subjects.get(1).getName(), "Guitar");
    assertEquals(subjects.get(1).getLessonLength(), 45);
    assertEquals(subjects.get(1).getClassRange(), List.of(1, 7));
    assertTrue(subjects.get(1).isItn());
  }

  @Test
  public void shouldDeleteSubject() {
    List<Subject> subjects = subjectPersistence.getAll();
    assertEquals(subjects.size(), 2);
    subjectPersistence.delete(1L);
    subjects = subjectPersistence.getAll();
    assertEquals(subjects.size(), 1);
  }
}
