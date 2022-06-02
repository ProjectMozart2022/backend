package model.report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import org.junit.Test;

public class TestSummaryReport {
  private final SummaryReport emptySummaryReport = new SummaryReport();
  private final TeacherReport teacherReport = new TeacherReport("xd", "xd", "xd", 40);
  private final SummaryReport nonEmptySummaryReport = new SummaryReport(List.of(teacherReport));

  @Test
  public void testGetTeacherReports() {
    assertNull(emptySummaryReport.getTeacherReports());
    assertEquals(nonEmptySummaryReport.getTeacherReports(), List.of(teacherReport));
    assertEquals(nonEmptySummaryReport.getTeacherReports().size(), 1);
  }

  @Test
  public void testSetTeacherReports() {
    assertNull(emptySummaryReport.getTeacherReports());
    emptySummaryReport.setTeacherReports(List.of(teacherReport));
    assertEquals(emptySummaryReport.getTeacherReports(), List.of(teacherReport));
    assertEquals(emptySummaryReport.getTeacherReports().size(), 1);
  }
}
