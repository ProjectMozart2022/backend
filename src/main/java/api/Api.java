package api;

import static spark.Spark.*;

import api.security.Cors;
import api.security.Firebase;
import com.google.gson.Gson;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class Api {
  private static final Gson gson = new Gson();
  private static final SubjectApi SUBJECT_API = new SubjectApi();
  private static final TeacherApi teacherApi = new TeacherApi();
  private static final StudentApi studentApi = new StudentApi();

  public static void main(String[] args) {
    Config config = ConfigFactory.load();
    port(config.getInt("mozart.api.port"));
    Firebase.enable(config.getString("mozart.security.serviceAccountKey"));
    Cors.enable();

    path(
        "/api",
        () -> {
          path(
              "/admin",
              () -> {
                path(
                    "/lesson",
                    () -> post("", (request, response) -> "lesson created", gson::toJson));

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
                      get("", SUBJECT_API::getAll, gson::toJson);
                      post("", SUBJECT_API::add, gson::toJson);
                      put("", SUBJECT_API::update, gson::toJson);
                      delete("", SUBJECT_API::delete, gson::toJson);
                    });
              });
          path(
              "/teacher",
              () -> {
                path("/ping", () -> get("", (request, response) -> "pong"));
              });
        });
  }
}
