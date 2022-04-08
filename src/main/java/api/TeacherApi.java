package api;

import static java.net.HttpURLConnection.HTTP_OK;

import com.google.gson.Gson;
import java.util.List;
import java.util.logging.Logger;
import model.Teacher;
import persistence.TeacherPersistence;
import spark.Request;
import spark.Response;

public class TeacherApi {
  private static final Gson gson = new Gson();
  private static final TeacherPersistence persistence = new TeacherPersistence();
  private static final Logger log = Logger.getLogger(TeacherApi.class.getName());

  public List<Teacher> getTeachers(Request request, Response response) {
    return persistence.getTeachers();
  }

  public Teacher getTeacher(Request request, Response response) {
    return persistence.getTeacher(Long.parseLong(request.queryParams("id")));
  }

  public void addTeacher(long accountId, Teacher teacher) {
    persistence.addTeacher(accountId, teacher);
    log.info("Successfully created teacher");
  }

  public String updateTeacher(Request request, Response response) {
    persistence.updateTeacher(gson.fromJson(request.body(), Teacher.class));
    response.status(HTTP_OK);
    return "Successfully updated teacher";
  }

  public void deleteTeacherById(long id) {
    persistence.deleteTeacher(id);
    log.info("Successfully deleted teacher");
  }
}
