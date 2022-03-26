package api;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;

import com.google.gson.Gson;
import java.util.List;
import model.Student;
import persistence.StudentPersistence;
import spark.Request;
import spark.Response;

public class StudentApi {
  private static final Gson gson = new Gson();
  private static final StudentPersistence persistence = new StudentPersistence();

  public List<Student> getStudents(Request request, Response response) {
    return persistence.getStudents();
  }

  public String addStudent(Request request, Response response) {
    persistence.addStudent(gson.fromJson(request.body(), Student.class));
    response.status(HTTP_CREATED);
    return "successfully created student";
  }

  public String updateStudent(Request request, Response response) {
    persistence.updateStudent(gson.fromJson(request.body(), Student.class));
    response.status(HTTP_OK);
    return "Successfully updated student";
  }

  public String deleteStudent(Request request, Response response) {
    persistence.deleteStudent(Long.parseLong(request.queryParams("id")));
    response.status(HTTP_OK);
    return "Successfully deleted student";
  }
}
