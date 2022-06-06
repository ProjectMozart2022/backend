package api;

import com.google.gson.Gson;
import model.Subject;
import teacherPersistence.LessonPersistence;
import teacherPersistence.SubjectPersistence;
import spark.Request;
import spark.Response;

public class LessonApi {
  private final LessonPersistence lessonPersistence;
  private final SubjectPersistence subjectPersistence;
  private static final Gson gson = new Gson();

  public LessonApi(LessonPersistence lessonPersistence, SubjectPersistence subjectPersistence) {
    this.lessonPersistence = lessonPersistence;
    this.subjectPersistence = subjectPersistence;
  }

  public String add(Request request, Response response) {
    lessonPersistence.add(
        Long.parseLong(request.queryParams("studentId")),
        request.queryParams("teacherId"),
        Long.parseLong(request.queryParams("subjectId")));
    return "lesson created";
  }

  public String addWithItn(Request request, Response response) {
    Long subjectId = subjectPersistence.add(gson.fromJson(request.body(), Subject.class));
    lessonPersistence.add(
        Long.parseLong(request.queryParams("studentId")),
        request.queryParams("teacherId"),
        subjectId);
    return "lesson created";
  }
}
