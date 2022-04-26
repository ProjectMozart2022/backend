package api;

import static spark.Spark.*;

import api.security.Cors;
import com.google.gson.Gson;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class Api {
  private static final Gson gson = new Gson();
  private static final SubjectApi subjectApi = new SubjectApi();
  private static final TeacherApi teacherApi = new TeacherApi();
  private static final StudentApi studentApi = new StudentApi();
  private static final LessonApi lessonApi = new LessonApi();
  private static final ReportApi reportApi = new ReportApi();

  public static void main(String[] args) {
    Config config = ConfigFactory.load();
    port(config.getInt("mozart.api.port"));
    //    Firebase.enable(config.getString("mozart.security.serviceAccountKey"));
    Cors.enable();

    path(
        "/api",
        () -> {
          path(
              "/admin",
              () -> {
                path(
                    "/student",
                    () -> {
                      get("", studentApi::getAll, gson::toJson);
                      post("", studentApi::add, gson::toJson);
                      put("", studentApi::update, gson::toJson);
                      delete("", studentApi::delete, gson::toJson);
                    });
                path(
                    "/teacher",
                    () -> {
                      get("", teacherApi::getAll, gson::toJson);
                      post("", teacherApi::add, gson::toJson);
                      put("", teacherApi::update, gson::toJson);
                      delete("", teacherApi::delete, gson::toJson);
                    });
                path(
                    "/subject",
                    () -> {
                      get("", subjectApi::getAll, gson::toJson);
                      post("", subjectApi::add, gson::toJson);
                      put("", subjectApi::update, gson::toJson);
                      delete("", subjectApi::delete, gson::toJson);
                    });
                path("/lesson", () -> get("", lessonApi::add, gson::toJson));
                path("/report", () -> get("", reportApi::getForAll, gson::toJson));
              });
          path(
              "/teacher",
              () -> {
                path("/report", () -> get("", teacherApi::getOne, gson::toJson));
              });
        });
  }
}
