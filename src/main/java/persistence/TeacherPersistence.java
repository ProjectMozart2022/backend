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

    public void addTeacher(String firstName, String lastName) {
        jdbi.inTransaction(
                handle ->
                        handle
                                .createUpdate(create().locate("queries/teacher/add_student"))
                                .bind("first_name", firstName)
                                .bind("last_name", lastName)
                                .execute());
    }

    public void updateTeacher(Teacher requestedTeacher) {
        jdbi.inTransaction(
                handle ->
                        handle
                                .createUpdate(create().locate("queries/teacher/update_teacher"))
                                .bind("first_name", requestedTeacher.firstName())
                                .bind("last_name", requestedTeacher.lastName())
                                .execute());
    }

    public void deleteTeacher(long id) {
        jdbi.inTransaction(handle ->
                handle.createUpdate(create().locate("queries/teacher/delete_teacher"))
                        .bind("id", id)
                        .execute());
    }
}
