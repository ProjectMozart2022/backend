package api;

import static spark.Spark.*;

import api.security.Cors;
import api.security.Firebase;
import com.google.gson.Gson;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class Api {
  private static final Gson gson = new Gson();
  private static final ProfileApi profileApi = new ProfileApi();
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
                    "/student",
                    () -> {
                      get("", studentApi::getStudents, gson::toJson);
                      post("", studentApi::addStudent, gson::toJson);
                      put("", studentApi::updateStudent, gson::toJson);
                      delete("", studentApi::deleteStudent, gson::toJson);
                    });
                path(
                    "/teacher",
                    () -> {
                      get("", teacherApi::getTeachers, gson::toJson);
                      post("", teacherApi::addTeacher, gson::toJson);
                      put("", teacherApi::updateTeacher, gson::toJson);
                      delete("", teacherApi::deleteTeacher, gson::toJson);
                    });
                path(
                    "/profile",
                    () -> {
                      get("", profileApi::getProfiles, gson::toJson);
                      post("", profileApi::addProfile, gson::toJson);
                      put("", profileApi::updateProfile, gson::toJson);
                      delete("", profileApi::deleteProfile, gson::toJson);
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
