package persistence;

import static org.jdbi.v3.core.locator.ClasspathSqlLocator.create;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import model.*;

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

  public Subject getOne(Long subjectId) {
    return jdbi.inTransaction(
        handle ->
            handle
                .createQuery(create().locate("queries/subject/select_one"))
                .bind("id", subjectId)
                .mapToBean(Subject.class)
                .one());
  }

  public List<Subject> getAllByTeacher(Teacher teacher) {
    return jdbi.inTransaction(
            handle ->
                handle
                    .createQuery(create().locate("queries/subject/select_by_teacher"))
                    .bind("teacher_id", teacher.getFirebaseId())
                    .map(
                        (rs, ctx) ->
                            new Subject(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getInt("lesson_length"),
                                Arrays.asList((Integer[]) rs.getArray("class_range").getArray()),
                                rs.getBoolean("is_itn"),
                                rs.getBoolean("is_mandatory"),
                                rs.getBoolean("is_instrument_related"))).list());
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
