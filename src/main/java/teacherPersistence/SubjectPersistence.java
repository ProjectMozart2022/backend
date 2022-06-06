package teacherPersistence;

import static org.jdbi.v3.core.locator.ClasspathSqlLocator.create;

import java.util.List;
import java.util.stream.Collectors;

import model.Lesson;
import model.Student;
import model.Subject;
import model.Teacher;

public class SubjectPersistence extends Persistence {
  public SubjectPersistence() {
    super();
  }

  public List<Subject> getAll() {
    return jdbi.inTransaction(
        handle ->
            handle
                .createQuery(create().locate("queries/subject/select_all"))
                .mapToBean(Subject.class)
                .list());
  }

  public Subject getOne(int subjectId) {
    return jdbi.inTransaction(
        handle ->
            handle
                .createQuery(create().locate("queries/subject/select_one"))
                .bind("id", subjectId)
                .mapToBean(Subject.class)
                .one());
  }

  public List<Subject> getAllByTeacher(Teacher teacher) {
    return getAll().stream()
        .filter(subject -> teacher.getKnownSubjects().contains(subject))
        .collect(Collectors.toList());
  }

  public List<Subject> getAllUnassignedByStudent(Student student) {
    return getAll().stream()
        .filter(
            subject ->
                subject.getClassRange().contains(student.getClassNumber())
                    && !student.getLessons().stream()
                        .map(Lesson::getSubject)
                        .collect(Collectors.toList())
                        .contains(subject))
        .collect(Collectors.toList());
  }

  public Long add(Subject subject) {
    return jdbi.inTransaction(
        handle ->
            handle
                .createQuery(create().locate("queries/subject/add"))
                .bind("name", subject.getName())
                .bind("lesson_length", subject.getLessonLength())
                .bind("class_range", subject.getClassRangeAsArray())
                .bind("is_itn", subject.isItn())
                .bind("is_mandatory", subject.isMandatory())
                .bind("is_instrument_related", subject.isInstrumentRelated())
                .mapTo(Long.class)
                .first());
  }

  public void update(Subject subject) {
    jdbi.inTransaction(
        handle ->
            handle
                .createUpdate(create().locate("queries/subject/update"))
                .bind("id", subject.getId())
                .bind("name", subject.getName())
                .bind("lesson_length", subject.getLessonLength())
                .bind("class_range", subject.getClassRangeAsArray())
                .bind("is_itn", subject.isItn())
                .bind("is_mandatory", subject.isMandatory())
                .bind("is_instrument_related", subject.isInstrumentRelated())
                .execute());
  }

  public void delete(long id) {
    jdbi.inTransaction(
        handle ->
            handle
                .createUpdate(create().locate("queries/subject/delete"))
                .bind("id", id)
                .execute());
  }
}
