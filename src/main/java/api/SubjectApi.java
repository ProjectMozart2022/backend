package api;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;

import com.google.gson.Gson;
import java.util.List;
import model.Subject;
import persistence.SubjectPersistence;
import spark.Request;
import spark.Response;

public class SubjectApi {
  private static final Gson gson = new Gson();
  private static final SubjectPersistence persistence = new SubjectPersistence();

  public List<Subject> getAll(Request request, Response response) {
    return persistence.getAll();
  }

  public List<Subject> getAllFilteredByTeacher(Request request, Response response) {
    String teacherId = request.queryParams("teacherId");
    return persistence.getAll();
  }

  public List<Subject> getAllFilteredByStudent(Request request, Response response) {
    String studentId = request.queryParams("studentId");
    return persistence.getAll();
  }

  public String add(Request request, Response response) {
    persistence.add(gson.fromJson(request.body(), Subject.class));
    response.status(HTTP_CREATED);
    return "Successfully created subject";
  }

  public String update(Request request, Response response) {
    persistence.update(gson.fromJson(request.body(), Subject.class));
    response.status(HTTP_OK);
    return "Successfully updated subject";
  }

  public String delete(Request request, Response response) {
    persistence.delete(Long.parseLong(request.queryParams("id")));
    response.status(HTTP_OK);
    return "Successfully deleted subject";
  }
}
