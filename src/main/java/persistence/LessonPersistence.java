package persistence;

import static org.jdbi.v3.core.locator.ClasspathSqlLocator.create;

import java.util.Arrays;
import java.util.List;
import model.Lesson;
import model.Student;
import model.Subject;
import model.Teacher;

public class LessonPersistence extends Persistence {
  public void add(int studentId, String teacherId, int profileId) {
    jdbi.inTransaction(
        handle ->
            handle
                .createUpdate(create().locate("queries/lesson/add"))
                .bind("student_id", studentId)
                .bind("teacher_id", teacherId)
                .bind("profile_id", profileId)
                .execute());
  }

  public List<Lesson> getAllByStudent(long studentId) {
    return jdbi.inTransaction(
        handle ->
            handle
                .createQuery(create().locate("queries/lesson/select_with_student_id"))
                .bind("student_id", studentId)
                .map(
                    (rs, ctx) ->
                        new Lesson(
                            new Student(
                                rs.getInt("s.id"),
                                rs.getString("s.first_name"),
                                rs.getString("s.last_name"),
                                rs.getInt("s.class_number")),
                            new Teacher(
                                rs.getString("t.firebase_id"),
                                rs.getString("t.first_name"),
                                rs.getString("t.last_name"),
                                rs.getString("t.email"),
                                rs.getString("t.password")),
                            new Subject(
                                rs.getInt("p.id"),
                                rs.getString("p.name"),
                                rs.getInt("p.lesson_length"),
                                Arrays.asList(rs.getObject("p.class_range", Integer[].class)),
                                rs.getBoolean("p.is_itn"))))
                .list());
  }

  public List<Lesson> getAllByTeacher(String teacherId) {
    return jdbi.inTransaction(
        handle ->
            handle
                .createQuery(create().locate("queries/lesson/select_with_teacher_id"))
                .bind("teacher_id", teacherId)
                .map(
                    (rs, ctx) ->
                        new Lesson(
                            new Student(
                                rs.getInt("s.id"),
                                rs.getString("s.first_name"),
                                rs.getString("s.last_name"),
                                rs.getInt("s.class_number")),
                            new Teacher(
                                rs.getString("t.firebase_id"),
                                rs.getString("t.first_name"),
                                rs.getString("t.last_name"),
                                rs.getString("t.email"),
                                rs.getString("t.password")),
                            new Subject(
                                rs.getInt("p.id"),
                                rs.getString("p.name"),
                                rs.getInt("p.lesson_length"),
                                Arrays.asList(rs.getObject("p.class_range", Integer[].class)),
                                rs.getBoolean("p.is_itn"))))
                .list());
  }
}
