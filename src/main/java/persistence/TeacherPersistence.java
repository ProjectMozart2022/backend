package persistence;

import static org.jdbi.v3.core.locator.ClasspathSqlLocator.create;

import java.util.List;

import api.security.Account;
import model.Teacher;

public class TeacherPersistence extends Persistence {
  public TeacherPersistence() {
    super();
  }

  public List<Teacher> getTeachers() {
    return jdbi.inTransaction(
        handle ->
            handle
                .createQuery(create().locate("queries/teacher/select_teachers"))
                .mapToBean(Teacher.class)
                .list());
  }

  public void addTeacher(long accountId, Teacher teacher) {
    jdbi.inTransaction(
        handle ->
            handle
                .createUpdate(create().locate("queries/teacher/add_teacher"))
                    .bind("account_id", accountId)
                .bind("first_name", teacher.getFirstName())
                .bind("last_name", teacher.getLastName())
                .execute());
  }

  public void updateTeacher(Teacher teacher) {
    jdbi.inTransaction(
        handle ->
            handle
                .createUpdate(create().locate("queries/teacher/update_teacher"))
                    .bind("id", teacher.getId())
                .bind("first_name", teacher.getFirstName())
                .bind("last_name", teacher.getLastName())
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

  public Teacher getTeacher(long id) {
    return jdbi.inTransaction(
            handle ->
                    handle
                            .createQuery(create().locate("queries/teacher/select_teacher"))
                            .bind("id", id)
                            .mapToBean(Teacher.class)
                            .one());
  }
}
