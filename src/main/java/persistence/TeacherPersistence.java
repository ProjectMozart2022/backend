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
                .createQuery(create().locate("queries/teacher/select_teachers"))
                .mapToBean(Teacher.class)
                .list());
  }

  public void addTeacher(Teacher teacher) {
    jdbi.inTransaction(
        handle ->
            handle
                .createUpdate(create().locate("queries/teacher/add_teacher"))
                .bind("firebase_id", teacher.getFirebaseId())
                .bind("first_name", teacher.getFirstName())
                .bind("last_name", teacher.getLastName())
                .bind("password", teacher.getPassword())
                .bind("email", teacher.getEmail())
                .execute());
  }

  public void updateTeacher(Teacher teacher) {
    jdbi.inTransaction(
        handle ->
            handle
                .createUpdate(create().locate("queries/teacher/update_teacher"))
                .bind("firebase_id", teacher.getFirebaseId())
                .bind("first_name", teacher.getFirstName())
                .bind("last_name", teacher.getLastName())
                .bind("password", teacher.getPassword())
                .bind("email", teacher.getEmail())
                .execute());
  }

  public void deleteTeacher(String firebaseId) {
    jdbi.inTransaction(
        handle ->
            handle
                .createUpdate(create().locate("queries/teacher/delete_teacher"))
                .bind("firebase_id", firebaseId)
                .execute());
  }

  public Teacher getTeacher(String firebaseId) {
    return jdbi.inTransaction(
        handle ->
            handle
                .createQuery(create().locate("queries/teacher/select_teacher"))
                .bind("firebase_id", firebaseId)
                .mapToBean(Teacher.class)
                .one());
  }
}
