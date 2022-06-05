package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import org.junit.jupiter.api.Test;

public class TestStudent {
  private final Student emptyStudent = new Student();
  private final Student testStudent = new Student(1L, "Henryk", "Sienkiewicz", 2);

  private final Lesson emptyLesson = new Lesson();

  @Test
  public void shouldGetIdOfStudent() {
    assertEquals(emptyStudent.getId(), 0);
    assertEquals(testStudent.getId(), 1L);
  }

  @Test
  public void shouldGetFirstNameOfStudent() {
    assertNull(emptyStudent.getFirstName());
    assertEquals(testStudent.getFirstName(), "Henryk");
  }

  @Test
  public void shouldGetLastNameOfStudent() {
    assertNull(emptyStudent.getLastName());
    assertEquals(testStudent.getLastName(), "Sienkiewicz");
  }

  @Test
  public void shouldGetClassNumberOfStudent() {
    assertEquals(emptyStudent.getClassNumber(), 0);
    assertEquals(testStudent.getClassNumber(), 2);
  }

  @Test
  public void shouldSetStudentId() {
    emptyStudent.setId(2L);
    assertEquals(emptyStudent.getId(), 2L);
  }

  @Test
  public void shouldSetFirstName() {
    emptyStudent.setFirstName("Adam");
    assertEquals(emptyStudent.getFirstName(), "Adam");
  }

  @Test
  public void shouldSetLastName() {
    emptyStudent.setLastName("Mickiewicz");
    assertEquals(emptyStudent.getLastName(), "Mickiewicz");
  }

  @Test
  public void shouldSetClassNumberForStudent() {
    emptyStudent.setClassNumber(3);
    assertEquals(emptyStudent.getClassNumber(), 3);
  }

  @Test
  public void shouldGetLessonOfStudent() {
    List<Lesson> lessons = emptyStudent.getLessons();
    assertNull(lessons);
  }

  @Test
  public void shouldSetLessonOfStudent() {
    emptyStudent.setLessons(List.of(emptyLesson));
    List<Lesson> lessons = emptyStudent.getLessons();
    assertEquals(lessons.size(), 1);
    assertEquals(lessons.get(0), emptyLesson);
  }
}
