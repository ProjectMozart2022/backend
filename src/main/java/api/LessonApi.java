package api;

import persistence.LessonPersistence;
import spark.Request;
import spark.Response;

public class LessonApi {
  private static final LessonPersistence persistence = new LessonPersistence();

  public String add(Request request, Response response) {
    persistence.add(
        Long.parseLong(request.queryParams("studentId")),
        request.queryParams("teacherId"),
        Long.parseLong(request.queryParams("subjectId")));
    return "lesson created";
  }
}
