package persistence;

import model.Instrument;
import model.Student;
import model.Subject;
import model.Teacher;
import org.jdbi.v3.core.statement.Batch;
import org.jdbi.v3.core.statement.PreparedBatch;

import java.util.List;
import java.util.stream.Collectors;

import static org.jdbi.v3.core.locator.ClasspathSqlLocator.create;

public class TeacherPersistence extends Persistence {
  private final LessonPersistence lessonPersistence = new LessonPersistence();
  private final SubjectPersistence subjectPersistence = new SubjectPersistence();

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

  public void add(Teacher teacher) {
    jdbi.inTransaction(
        handle -> {
            handle
              .createUpdate(create().locate("queries/teacher/add"))
              .bind("firebase_id", teacher.getFirebaseId())
              .bind("first_name", teacher.getFirstName())
              .bind("last_name", teacher.getLastName())
              .bind("password", teacher.getPassword())
              .bind("email", teacher.getEmail())
              .bind("minimal_num_of_hours", teacher.getMinimalNumOfHours())
              .bindByType("taught_instruments", teacher.getTaughtInstruments(), Instrument[].class)
              .execute();
            PreparedBatch batch = handle.prepareBatch(create().locate("queries/teacher/add_known_subjects"));
            teacher.getKnownSubjects().stream()
                    .map(Subject::getId)
                    .forEach(
                            id ->
                                    batch
                                            .bind("teacher_id", teacher.getFirebaseId())
                                            .bind("subject_id", id)
                                            .add());
            return batch.execute();
        });
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
