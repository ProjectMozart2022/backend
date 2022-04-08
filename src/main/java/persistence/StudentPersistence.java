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
                .createQuery(create().locate("queries/student/select_students"))
                .mapToBean(Student.class)
                .list());
  }

  public void addStudent(Student student) {
    jdbi.inTransaction(
        handle ->
            handle
                .createUpdate(create().locate("queries/student/add_student"))
                .bind("first_name", student.getFirstName())
                .bind("last_name", student.getLastName())
                .bind("class_number", student.getClassNumber())
                .execute());
  }

  public void updateStudent(Student student) {
    jdbi.inTransaction(
        handle ->
            handle
                .createUpdate(create().locate("queries/student/update_student"))
                .bind("id", student.getId())
                .bind("first_name", student.getFirstName())
                .bind("last_name", student.getLastName())
                .bind("class_number", student.getClassNumber())
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
