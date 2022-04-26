package model.report;

import java.util.List;

public class SummaryReport {
  private List<TeacherReport> teacherReports;

  public SummaryReport() {}

  public SummaryReport(List<TeacherReport> teacherReports) {
    this.teacherReports = teacherReports;
  }

  public List<TeacherReport> getTeacherReports() {
    return teacherReports;
  }

  public void setTeacherReports(List<TeacherReport> teacherReports) {
    this.teacherReports = teacherReports;
  }
}
