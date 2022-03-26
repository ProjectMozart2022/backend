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
                .mapToBean(Student.class)
                .list());
  }

  public void addStudent(Student createdStudent) {
    jdbi.inTransaction(
        handle ->
            handle
                .createUpdate(create().locate("queries/student/add_student"))
                .bind("first_name", createdStudent.getFirstName())
                .bind("last_name", createdStudent.getLastName())
                .bind("class_number", createdStudent.getClassNumber())
                .execute());
  }

  public void updateStudent(Student updatedStudent) {
    jdbi.inTransaction(
        handle ->
            handle
                .createUpdate(create().locate("queries/student/update_student"))
                .bind("id", updatedStudent.getId())
                .bind("first_name", updatedStudent.getFirstName())
                .bind("last_name", updatedStudent.getLastName())
                .bind("class_number", updatedStudent.getClassNumber())
                .execute());
  }

  public void deleteStudent(long id) {
    jdbi.inTransaction(
        handle ->
            handle
                .createUpdate(create().locate("queries/student/delete_student"))
                .bind("id", id)
                .execute());
  }
}
