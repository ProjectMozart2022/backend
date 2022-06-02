package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import org.junit.Test;

public class TestLesson {
  private final Student nonEmptyStudent = new Student(1L, "Antoine", "Jakiś", 1);
  private final Teacher nonEmptyTeacher =
      new Teacher("1a", "Antoni", "Mickiewicz", "antoni@gmail.com", "xd");
  private final Subject nonEmptySubject = new Subject(2L, "Music", 40, Arrays.asList(1, 2), false);
  private final Lesson emptyLesson = new Lesson();
  private final Lesson nonEmptyLesson =
      new Lesson(nonEmptyStudent, nonEmptyTeacher, nonEmptySubject);

  @Test
  public void testGetProfile() {
    assertNull(emptyLesson.getProfile());
    assertEquals(nonEmptyLesson.getProfile(), nonEmptySubject);
  }

  @Test
  public void testSetProfile() {
    assertNull(emptyLesson.getProfile());
    emptyLesson.setProfile(nonEmptySubject);
    assertEquals(emptyLesson.getProfile(), nonEmptySubject);
  }

  @Test
  public void testGetTeacher() {
    assertNull(emptyLesson.getTeacher());
    assertEquals(nonEmptyLesson.getTeacher(), nonEmptyTeacher);
  }

  @Test
  public void testSetTeacher() {
    assertNull(emptyLesson.getTeacher());
    emptyLesson.setTeacher(nonEmptyTeacher);
    assertEquals(emptyLesson.getTeacher(), nonEmptyTeacher);
  }

  @Test
  public void testGetStudent() {
    assertNull(emptyLesson.getStudent());
    assertEquals(nonEmptyLesson.getStudent(), nonEmptyStudent);
  }

  @Test
  public void testSetStudent() {
    assertNull(emptyLesson.getStudent());
    emptyLesson.setStudent(nonEmptyStudent);
    assertEquals(emptyLesson.getStudent(), nonEmptyStudent);
  }
}
