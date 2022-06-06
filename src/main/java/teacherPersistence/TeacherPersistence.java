package teacherPersistence;

import static org.jdbi.v3.core.locator.ClasspathSqlLocator.create;

import java.util.List;

import model.Student;
import model.Subject;
import model.Teacher;

public class TeacherPersistence extends Persistence {
  private final LessonPersistence lessonPersistence = new LessonPersistence();
  private final SubjectPersistence subjectPersistence = new SubjectPersistence();
  private final StudentPersistence studentPersistance = new StudentPersistence();

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
        teacher -> {
          teacher.setLessons(lessonPersistence.getAllByTeacher(teacher.getFirebaseId()));
          teacher.setKnownSubjects(subjectPersistence.getAllByTeacher(teacher));
        });
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
    teacher.setKnownSubjects(subjectPersistence.getAllByTeacher(teacher));
    return teacher;
  }

  public List<Teacher> getAllByStudentAndSubject(String studentId, int subjectId) {
    List<Teacher> teachers = getAll();
    Subject subject = subjectPersistence.getOne(subjectId);
    Student student = studentPersistance.getOne(Integer.parseInt(studentId));
    return teachers;
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
                .bind("minimal_num_of_hours", teacher.getMinimalNumOfHours())
                .bind("taught_instruments", teacher.getTaughtInstruments())
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
                .bind("minimal_num_of_hours", teacher.getMinimalNumOfHours())
                .bind("taught_instruments", teacher.getTaughtInstruments())
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
