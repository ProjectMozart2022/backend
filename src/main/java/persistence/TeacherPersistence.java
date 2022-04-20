package persistence;

import static org.jdbi.v3.core.locator.ClasspathSqlLocator.create;

import java.util.List;
import model.Teacher;

public class TeacherPersistence extends Persistence {
  private final LessonPersistence lessonPersistence = new LessonPersistence();

  public TeacherPersistence() {
    super();
  }

  public List<Teacher> getAll() {
    List<Teacher> teachers =
        jdbi.inTransaction(
            handle ->
                handle
                    .createQuery(create().locate("queries/teacher/select_all"))
                    .mapToBean(Teacher.class)
                    .list());
    teachers.forEach(
        teacher -> teacher.setLessons(lessonPersistence.getAllByTeacher(teacher.getFirebaseId())));
    return teachers;
  }

  public Teacher getOne(String firebaseId) {
    Teacher teacher =
        jdbi.inTransaction(
            handle ->
                handle
                    .createQuery(create().locate("queries/teacher/select_one"))
                    .bind("firebase_id", firebaseId)
                    .mapToBean(Teacher.class)
                    .one());
    teacher.setLessons(lessonPersistence.getAllByTeacher(teacher.getFirebaseId()));
    return teacher;
  }

  public void add(Teacher teacher) {
    jdbi.inTransaction(
        handle ->
            handle
                .createUpdate(create().locate("queries/teacher/add"))
                .bind("firebase_id", teacher.getFirebaseId())
                .bind("first_name", teacher.getFirstName())
                .bind("last_name", teacher.getLastName())
                .bind("password", teacher.getPassword())
                .bind("email", teacher.getEmail())
                .execute());
  }

  public void update(Teacher teacher) {
    jdbi.inTransaction(
        handle ->
            handle
                .createUpdate(create().locate("queries/teacher/update"))
                .bind("firebase_id", teacher.getFirebaseId())
                .bind("first_name", teacher.getFirstName())
                .bind("last_name", teacher.getLastName())
                .bind("password", teacher.getPassword())
                .bind("email", teacher.getEmail())
                .execute());
  }

  public void delete(String firebaseId) {
    jdbi.inTransaction(
        handle ->
            handle
                .createUpdate(create().locate("queries/teacher/delete"))
                .bind("firebase_id", firebaseId)
                .execute());
  }
}
