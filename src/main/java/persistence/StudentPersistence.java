package persistence;

import static org.jdbi.v3.core.locator.ClasspathSqlLocator.create;

import java.util.List;
import model.Student;

public class StudentPersistence extends Persistence {
  private final LessonPersistence lessonPersistence = new LessonPersistence();

  public StudentPersistence() {
    super();
  }

  public List<Student> getAll() {
    List<Student> students =
        jdbi.inTransaction(
            handle ->
                handle
                    .createQuery(create().locate("queries/student/select_all"))
                    .mapToBean(Student.class)
                    .list());

    students.forEach(
        student -> student.setLessons(lessonPersistence.getAllByStudent(student.getId())));
    return students;
  }

  public Student getOne(int studentId) {
    Student student =
        jdbi.inTransaction(
            handle ->
                handle
                    .createQuery(create().locate("queries/student/select_one"))
                    .bind("student_id", studentId)
                    .mapToBean(Student.class)
                    .one());
    student.setLessons(lessonPersistence.getAllByStudent(student.getId()));
    return student;
  }

  public void add(Student student) {
    jdbi.inTransaction(
        handle ->
            handle
                .createUpdate(create().locate("queries/student/add"))
                .bind("first_name", student.getFirstName())
                .bind("last_name", student.getLastName())
                .bind("class_number", student.getClassNumber())
                .execute());
  }

  public void update(Student student) {
    jdbi.inTransaction(
        handle ->
            handle
                .createUpdate(create().locate("queries/student/update"))
                .bind("id", student.getId())
                .bind("first_name", student.getFirstName())
                .bind("last_name", student.getLastName())
                .bind("class_number", student.getClassNumber())
                .execute());
  }

  public void delete(long id) {
    jdbi.inTransaction(
        handle ->
            handle
                .createUpdate(create().locate("queries/student/delete"))
                .bind("id", id)
                .execute());
  }
}
