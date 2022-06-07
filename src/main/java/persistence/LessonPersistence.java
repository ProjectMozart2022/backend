package persistence;

import static org.jdbi.v3.core.locator.ClasspathSqlLocator.create;

import java.util.Arrays;
import java.util.List;
import model.Lesson;
import model.Student;
import model.Subject;
import model.Teacher;

public class LessonPersistence extends Persistence {

  public LessonPersistence(String jdbcUrl, String username, String password, int maximumPoolSize) {
    super(jdbcUrl, username, password, maximumPoolSize);
  }

  public LessonPersistence() {
    super();
  }

  public void add(long studentId, String teacherId, long subjectId) {
    jdbi.inTransaction(
        handle ->
            handle
                .createUpdate(create().locate("queries/lesson/add"))
                .bind("student_id", studentId)
                .bind("teacher_id", teacherId)
                .bind("subject_id", subjectId)
                .execute());
  }

  public List<Lesson> getAllByStudent(long studentId) {
    return jdbi.inTransaction(
        handle ->
            handle
                .createQuery(create().locate("queries/lesson/select_by_student_id"))
                .bind("student_id", studentId)
                .map(
                    (rs, ctx) ->
                        new Lesson(
                            new Student(
                                rs.getInt("student_id"),
                                rs.getString("student_first_name"),
                                rs.getString("student_last_name"),
                                rs.getInt("student_class_number")),
                            new Teacher(
                                rs.getString("teacher_id"),
                                rs.getString("teacher_first_name"),
                                rs.getString("teacher_last_name"),
                                rs.getString("teacher_email"),
                                rs.getString("teacher_password")),
                            new Subject(
                                rs.getInt("subject_id"),
                                rs.getString("subject_name"),
                                rs.getInt("subject_lesson_length"),
                                Arrays.asList(
                                    (Integer[]) rs.getArray("subject_class_range").getArray()),
                                rs.getBoolean("subject_is_itn"))))
                .list());
  }

  public List<Lesson> getAllByTeacher(String teacherId) {
    return jdbi.inTransaction(
        handle ->
            handle
                .createQuery(create().locate("queries/lesson/select_by_teacher_id"))
                .bind("teacher_id", teacherId)
                .map(
                    (rs, ctx) ->
                        new Lesson(
                            new Student(
                                rs.getInt("student_id"),
                                rs.getString("student_first_name"),
                                rs.getString("student_last_name"),
                                rs.getInt("student_class_number")),
                            new Teacher(
                                rs.getString("teacher_id"),
                                rs.getString("teacher_first_name"),
                                rs.getString("teacher_last_name"),
                                rs.getString("teacher_email"),
                                rs.getString("teacher_password")),
                            new Subject(
                                rs.getInt("subject_id"),
                                rs.getString("subject_name"),
                                rs.getInt("subject_lesson_length"),
                                Arrays.asList(
                                    (Integer[]) rs.getArray("subject_class_range").getArray()),
                                rs.getBoolean("subject_is_itn"))))
                .list());
  }
}
