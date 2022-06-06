package api;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;

import com.google.gson.Gson;
import java.util.List;

import model.Subject;
import teacherPersistence.StudentPersistence;
import teacherPersistence.SubjectPersistence;
import teacherPersistence.TeacherPersistence;
import spark.Request;
import spark.Response;

public class SubjectApi {
  private static final Gson gson = new Gson();
  private final SubjectPersistence subjectPersistence;
  private final TeacherPersistence teacherPersistence;
  private final StudentPersistence studentPersistence;

  public SubjectApi(
      SubjectPersistence subjectPersistence,
      TeacherPersistence teacherPersistence,
      StudentPersistence studentPersistence) {
    this.subjectPersistence = subjectPersistence;
    this.teacherPersistence = teacherPersistence;
    this.studentPersistence = studentPersistence;
  }

  public List<Subject> getAll(Request request, Response response) {
    return subjectPersistence.getAll();
  }

  public List<Subject> getAllFilteredByTeacher(Request request, Response response) {
    return subjectPersistence.getAllByTeacher(
        teacherPersistence.getOne(request.queryParams("teacherId")));
  }

  public List<Subject> getAllFilteredByStudent(Request request, Response response) {
    return subjectPersistence.getAllUnassignedByStudent(
        studentPersistence.getOne(Integer.parseInt(request.queryParams("studentId"))));
  }

  public String add(Request request, Response response) {
    subjectPersistence.add(gson.fromJson(request.body(), Subject.class));
    response.status(HTTP_CREATED);
    return "Successfully created subject";
  }

  public String update(Request request, Response response) {
    subjectPersistence.update(gson.fromJson(request.body(), Subject.class));
    response.status(HTTP_OK);
    return "Successfully updated subject";
  }

  public String delete(Request request, Response response) {
    subjectPersistence.delete(Long.parseLong(request.queryParams("id")));
    response.status(HTTP_OK);
    return "Successfully deleted subject";
  }
}
