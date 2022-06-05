package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import org.junit.Test;

public class TestSubject {
  private final Subject emptySubject = new Subject();
  private final Subject nonEmptySubject = new Subject(1L, "Music", 40, Arrays.asList(1, 2), false);

  @Test
  public void shouldGetIdOfSubject() {
    assertEquals(emptySubject.getId(), 0);
    assertEquals(nonEmptySubject.getId(), 1L);
  }

  @Test
  public void shouldSetIdOfSubject() {
    assertEquals(emptySubject.getId(), 0);
    emptySubject.setId(1L);
    assertEquals(emptySubject.getId(), 1L);
  }

  @Test
  public void shouldGetNameOfSubject() {
    assertNull(emptySubject.getName());
    assertEquals(nonEmptySubject.getName(), "Music");
  }

  @Test
  public void shouldSetSubjectName() {
    assertNull(emptySubject.getName());
    emptySubject.setName("Music");
    assertEquals(emptySubject.getName(), "Music");
  }

  @Test
  public void shouldGetSubjectLength() {
    assertEquals(emptySubject.getLessonLength(), 0);
    assertEquals(nonEmptySubject.getLessonLength(), 40);
  }

  @Test
  public void shouldSetSubjectLength() {
    assertEquals(emptySubject.getLessonLength(), 0);
    emptySubject.setLessonLength(40);
    assertEquals(emptySubject.getLessonLength(), 40);
  }

  @Test
  public void shouldGetSubjectClasses() {
    assertNull(emptySubject.getClassRange());
    assertEquals(nonEmptySubject.getClassRange(), Arrays.asList(1, 2));
  }

  @Test
  public void shouldSetSubjectClasses() {
    assertNull(emptySubject.getClassRange());
    emptySubject.setClassRange(Arrays.asList(1, 2));
    assertEquals(emptySubject.getClassRange(), Arrays.asList(1, 2));
  }

  @Test
  public void shouldGetItnOfSubject() {
    assertFalse(emptySubject.isItn());
    assertFalse(nonEmptySubject.isItn());
  }

  @Test
  public void shouldSetItnOfSubject() {
    assertFalse(emptySubject.isItn());
    emptySubject.setItn(true);
    assertTrue(emptySubject.isItn());
  }
}
