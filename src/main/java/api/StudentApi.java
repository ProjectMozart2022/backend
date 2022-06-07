package api;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;

import com.google.gson.Gson;
import java.util.List;
import java.util.stream.Collectors;

import model.Lesson;
import model.Student;
import model.Subject;
import model.Teacher;
import persistence.StudentPersistence;
import persistence.SubjectPersistence;
import persistence.TeacherPersistence;
import spark.Request;
import spark.Response;

public class StudentApi {
  private static final Gson gson = new Gson();
  private final StudentPersistence studentPersistence;
  private final TeacherPersistence teacherPersistence;
  private final SubjectPersistence subjectPersistence;

  public StudentApi(
      StudentPersistence studentPersistence,
      TeacherPersistence teacherPersistence,
      SubjectPersistence subjectPersistence) {
    this.studentPersistence = studentPersistence;
    this.teacherPersistence = teacherPersistence;
    this.subjectPersistence = subjectPersistence;
  }

  public List<Student> getAll(Request request, Response response) {
    return studentPersistence.getAll();
  }

  public List<api.dto.Student> getAllByTeacherAndSubject(Request request, Response response) {
    Subject subject = subjectPersistence.getOne(Long.parseLong(request.queryParams("subjectId")));
    Teacher teacher = teacherPersistence.getOne(request.queryParams("teacherId"));
    return convertToDto(studentPersistence.getAll().stream()
        .filter(
            student ->
                subject.getClassRange().contains(student.getClassNumber())
                    && !student.getLessons().stream()
                        .map(Lesson::getSubject)
                        .collect(Collectors.toList())
                        .contains(subject)
                    && (!subject.isInstrumentRelated()
                        || teacher.getTaughtInstruments().contains(student.getMainInstrument())))
        .collect(Collectors.toList()));
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
