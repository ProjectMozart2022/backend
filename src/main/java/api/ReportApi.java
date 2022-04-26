package api;

import java.util.stream.Collectors;
import model.Teacher;
import model.report.SummaryReport;
import persistence.TeacherPersistence;
import spark.Request;
import spark.Response;

public class ReportApi {
  private static final TeacherPersistence teacherPersistence = new TeacherPersistence();

  public SummaryReport getForAll(Request request, Response response) {
    return new SummaryReport(
        teacherPersistence.getAll().stream().map(Teacher::report).collect(Collectors.toList()));
  }

  public Teacher getForOne(Request request, Response response) {
    return teacherPersistence.getOne(request.queryParams("firebaseId"));
  }
}
