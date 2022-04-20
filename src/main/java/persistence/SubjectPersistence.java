package persistence;

import static org.jdbi.v3.core.locator.ClasspathSqlLocator.create;

import java.util.List;
import model.Subject;

public class SubjectPersistence extends Persistence {
  public SubjectPersistence() {
    super();
  }

  public List<Subject> getSubjects() {
    return jdbi.inTransaction(
        handle ->
            handle
                .createQuery(create().locate("queries/subject/select_all"))
                .mapToBean(Subject.class)
                .list());
  }

  public void add(Subject subject) {
    jdbi.inTransaction(
        handle ->
            handle
                .createUpdate(create().locate("queries/subject/add"))
                .bind("name", subject.getName())
                .bind("lesson_length", subject.getLessonLength())
                .bind("class_range", subject.getClassRangeAsArray())
                .bind("is_itn", subject.isItn())
                .execute());
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
