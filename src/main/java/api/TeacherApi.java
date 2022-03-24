package api;

import static java.lang.Long.parseLong;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;

import com.google.gson.Gson;
import java.util.List;
import model.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.TeacherPersistence;
import spark.Request;
import spark.Response;

public class TeacherApi {
  private static final Logger log = LoggerFactory.getLogger(TeacherApi.class);
  private static final Gson gson = new Gson();
  private static final TeacherPersistence persistence = new TeacherPersistence();

  public List<Teacher> getTeachers(Request request, Response response) {
    return persistence.getTeachers();
  }

  public long addTeacher(Request request, Response response) {
    Teacher newTeacher = gson.fromJson(request.body(), Teacher.class);
    long id = persistence.addTeacher(newTeacher);
    response.status(HTTP_CREATED);
    response.body("Successfully created teacher with id [" + id + "]");
    return id;
  }

  public long updateTeacher(Request request, Response response) {
    Teacher updatedTeacher = gson.fromJson(request.body(), Teacher.class);
    long id = persistence.addTeacher(updatedTeacher);
    response.status(HTTP_OK);
    response.body("Successfully updated teacher with id [" + id + "]");
    return id;
  }

  public long deleteTeacher(Request request, Response response) {
    long id = parseLong(request.queryParams("id"));
    persistence.deleteTeacher(id);
    response.status(HTTP_OK);
    response.body("Successfully created profile with id [" + id + "]");
    return id;
  }
}
