package persistence;

import static org.jdbi.v3.core.locator.ClasspathSqlLocator.create;

import java.util.List;
import model.Student;

public class StudentPersistence extends Persistence {
  public StudentPersistence() {
    super();
  }

  public List<Student> getStudents() {
    return jdbi.inTransaction(
        handle ->
            handle
                .createQuery(create().locate("queries/student/select_student"))
                .mapTo(Student.class)
                .list());
  }

  public Student getStudent(long id) {
    return jdbi.inTransaction(
        handle ->
            handle
                .createQuery(create().locate("queries/student/select_student_by_id"))
                .bind("id", id)
                .mapTo(Student.class)
                .one());
  }

  public long addStudent(String firstName, String lastName, int classNumber) {
    return jdbi.inTransaction(
            handle ->
                handle
                    .createUpdate(create().locate("queries/student/add_student"))
                    .bind("first_name", firstName)
                    .bind("last_name", lastName)
                    .bind("class_number", classNumber)
                    .executeAndReturnGeneratedKeys("id"))
        .mapTo(Long.class)
        .one();
  }

  public long updateStudent(String firstName, String lastName, int classNumber) {
    return jdbi.inTransaction(
            handle ->
                handle
                    .createUpdate(create().locate("queries/student/update_student"))
                    .bind("first_name", firstName)
                    .bind("last_name", lastName)
                    .bind("class_number", classNumber)
                    .executeAndReturnGeneratedKeys("id"))
        .mapTo(Long.class)
        .one();
  }

  public void deleteStudent(long id) {
    jdbi.inTransaction(handle -> handle.execute(create().locate("queries/student/delete_student")));
  }
}
