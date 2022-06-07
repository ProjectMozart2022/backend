package model.report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.Test;

public class TestTeacherReport {
  private final TeacherReport emptyTeacherReport = new TeacherReport();
  private final TeacherReport teacherReport = new TeacherReport("xd", "xd", "xd", 40);

  @Test
  public void testGetFirebaseId() {
    assertNull(emptyTeacherReport.getFirebaseId());
    assertEquals(teacherReport.getFirebaseId(), "xd");
  }

  @Test
  public void testSetFirebaseId() {
    assertNull(emptyTeacherReport.getFirebaseId());
    emptyTeacherReport.setFirebaseId("xd");
    assertEquals(emptyTeacherReport.getFirebaseId(), "xd");
  }

  @Test
  public void testGetFirstName() {
    assertNull(emptyTeacherReport.getFirstName());
    assertEquals(teacherReport.getFirstName(), "xd");
  }

  @Test
  public void testSetFirstName() {
    assertNull(emptyTeacherReport.getFirstName());
    emptyTeacherReport.setFirstName("xd");
    assertEquals(emptyTeacherReport.getFirstName(), "xd");
  }

  @Test
  public void testGetLastName() {
    assertNull(emptyTeacherReport.getLastName());
    assertEquals(teacherReport.getLastName(), "xd");
  }

  @Test
  public void testSetLastName() {
    assertNull(emptyTeacherReport.getLastName());
    emptyTeacherReport.setLastName("xd");
    assertEquals(emptyTeacherReport.getLastName(), "xd");
  }

  @Test
  public void testGetMinutesInTotal() {
    assertEquals(emptyTeacherReport.getMinutesInTotal(), 0);
    assertEquals(teacherReport.getMinutesInTotal(), 40);
  }
}
