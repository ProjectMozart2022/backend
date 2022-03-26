package persistence;

import static org.jdbi.v3.core.locator.ClasspathSqlLocator.create;

import java.util.List;
import model.Teacher;

public class TeacherPersistence extends Persistence {
  public TeacherPersistence() {
    super();
  }

  public List<Teacher> getTeachers() {
    return jdbi.inTransaction(
        handle ->
            handle
                .createQuery(create().locate("queries/teacher/select_teacher"))
                .mapToBean(Teacher.class)
                .list());
  }

  public void addTeacher(Teacher createdTeacher) {
    jdbi.inTransaction(
        handle ->
            handle
                .createUpdate(create().locate("queries/teacher/add_teacher"))
                .bind("first_name", createdTeacher.getFirstName())
                .bind("last_name", createdTeacher.getLastName())
                .execute());
  }

  public void updateTeacher(Teacher requestedTeacher) {
    jdbi.inTransaction(
        handle ->
            handle
                .createUpdate(create().locate("queries/teacher/update_teacher"))
                .bind("first_name", requestedTeacher.getFirstName())
                .bind("last_name", requestedTeacher.getLastName())
                .execute());
  }

  public void deleteTeacher(long id) {
    jdbi.inTransaction(
        handle ->
            handle
                .createUpdate(create().locate("queries/teacher/delete_teacher"))
                .bind("id", id)
                .execute());
  }
}
