package api;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;

import com.google.gson.Gson;
import java.util.List;
import model.Student;
import teacherPersistence.StudentPersistence;
import spark.Request;
import spark.Response;

public class StudentApi {
  private static final Gson gson = new Gson();
  private final StudentPersistence studentPersistence;

  public StudentApi(StudentPersistence studentPersistence) {
    this.studentPersistence = studentPersistence;
  }

  public List<Student> getAll(Request request, Response response) {
    return studentPersistence.getAll();
  }

  public List<Student> getAllFilteredByTeacher(Request request, Response response) {
    // wez wszystkie przedmioty ktore sa dostepne dla ucznia, obowiazkowe i nieprzypisane i wez wszystkich nauczycieli
    // ktorzy moga uczyc tych przedmiotow
    String teacherId = request.queryParams("teacherId");
    return studentPersistence.getAll();
  }

  public String add(Request request, Response response) {
    studentPersistence.add(gson.fromJson(request.body(), Student.class));
    response.status(HTTP_CREATED);
    return "successfully created student";
  }

  public String update(Request request, Response response) {
    studentPersistence.update(gson.fromJson(request.body(), Student.class));
    response.status(HTTP_OK);
    return "Successfully updated student";
  }

  public String delete(Request request, Response response) {
    studentPersistence.delete(Long.parseLong(request.queryParams("id")));
    response.status(HTTP_OK);
    return "Successfully deleted student";
  }
}
