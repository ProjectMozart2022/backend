package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.Test;

public class TestTeacher {
  private final Teacher emptyTeacher = new Teacher();
  private final Teacher nonEmptyTeacher =
      new Teacher("not", "Andrzej", "Kowalski", "tata@gmail.com", "xd");

  @Test
  public void testGetFirstName() {
    assertNull(emptyTeacher.getFirstName());
    assertEquals("Andrzej", nonEmptyTeacher.getFirstName());
  }

  @Test
  public void testSetFirstName() {
    assertNull(emptyTeacher.getFirstName());
    emptyTeacher.setFirstName("Andrzej");
    assertEquals("Andrzej", emptyTeacher.getFirstName());
  }

  @Test
  public void testGetLastName() {
    assertNull(emptyTeacher.getLastName());
    assertEquals("Kowalski", nonEmptyTeacher.getLastName());
  }

  @Test
  public void testSetLastName() {
    assertNull(emptyTeacher.getLastName());
    emptyTeacher.setLastName("Karwiski");
    assertEquals("Karwiski", emptyTeacher.getLastName());
  }

  @Test
  public void testGetEmail() {
    assertNull(emptyTeacher.getEmail());
    assertEquals("tata@gmail.com", nonEmptyTeacher.getEmail());
  }

  @Test
  public void testSetEmail() {
    assertNull(emptyTeacher.getEmail());
    emptyTeacher.setEmail("tata_2@gmail.com");
    assertEquals("tata_2@gmail.com", emptyTeacher.getEmail());
  }

  @Test
  public void testGetFirebaseId() {
    assertNull(emptyTeacher.getFirebaseId());
    assertEquals("not", nonEmptyTeacher.getFirebaseId());
  }

  @Test
  public void testSetFirebaseId() {
    assertNull(emptyTeacher.getFirebaseId());
    emptyTeacher.setFirebaseId("xd");
    assertEquals("xd", emptyTeacher.getFirebaseId());
  }
}
