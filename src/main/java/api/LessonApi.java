package api;

import com.google.gson.Gson;
import model.Subject;
import persistence.LessonPersistence;
import persistence.SubjectPersistence;
import spark.Request;
import spark.Response;

public class LessonApi {
  private static final LessonPersistence persistence = new LessonPersistence();
  private static final SubjectPersistence subjectPersistence = new SubjectPersistence();
  private static final Gson gson = new Gson();

  public String add(Request request, Response response) {
    persistence.add(
        Long.parseLong(request.queryParams("studentId")),
        request.queryParams("teacherId"),
        Long.parseLong(request.queryParams("subjectId")));
    return "lesson created";
  }

  public String addWithItn(Request request, Response response) {
    Long subjectId = subjectPersistence.add(gson.fromJson(request.body(), Subject.class));
    persistence.add(
        Long.parseLong(request.queryParams("studentId")),
        request.queryParams("teacherId"),
        subjectId);
    return "lesson created";
  }
}
