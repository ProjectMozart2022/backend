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
                .mapTo(Teacher.class)
                .list());
  }

  public Teacher getTeacher(long id) {
    return jdbi.inTransaction(
        handle ->
            handle
                .createQuery(create().locate("queries/teacher/select_teacher_by_id"))
                .bind("id", id)
                .mapTo(Teacher.class)
                .one());
  }

  public long addTeacher(Teacher requestedTeacher) {
    return jdbi.inTransaction(
            handle ->
                handle
                    .createUpdate(create().locate("queries/teacher/add_student"))
                    .bind("first_name", requestedTeacher.firstName())
                    .bind("last_name", requestedTeacher.lastName())
                    .executeAndReturnGeneratedKeys("id"))
        .mapTo(Long.class)
        .one();
  }

  public long updateTeacher(Teacher requestedTeacher) {
    return jdbi.inTransaction(
            handle ->
                handle
                    .createUpdate(create().locate("queries/teacher/update_teacher"))
                    .bind("first_name", requestedTeacher.firstName())
                    .bind("last_name", requestedTeacher.lastName())
                    .executeAndReturnGeneratedKeys("id"))
        .mapTo(Long.class)
        .one();
  }

  public void deleteTeacher(long id) {
    jdbi.inTransaction(handle -> handle.execute(create().locate("queries/teacher/delete_teacher")));
  }
}
