package persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import model.Profile;
import model.Student;
import model.Teacher;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

import static org.jdbi.v3.core.locator.ClasspathSqlLocator.create;

public class Persistence {
    private final Jdbi jdbi;

    public Persistence(String url, String username, String password, int poolSize) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setMaximumPoolSize(poolSize);
        DataSource dataSource = new HikariDataSource(hikariConfig);
        jdbi = Jdbi.create(dataSource)
                .registerRowMapper(ConstructorMapper.factory(Profile.class))
                .registerRowMapper(ConstructorMapper.factory(Student.class))
                .registerRowMapper(ConstructorMapper.factory(Teacher.class));
    }


    public List<Student> students() {
        return jdbi.inTransaction(handle ->
                handle.createQuery(create().locate("select_student"))
                        .mapTo(Student.class)
                        .list()
        );
    }

    public Student getStudent(long id) {
        return jdbi.inTransaction(handle ->
                handle.createQuery(create().locate("select_student_by_id"))
                        .bind("id", id)
                        .mapTo(Student.class)
                        .one()
        );
    }


    public long addStudent(Student requestedStudent) {
        return jdbi.inTransaction(handle ->
                        handle.createUpdate(create().locate("add_student"))
                                .bind("first_name", requestedStudent.firstName())
                                .bind("last_name", requestedStudent.lastName())
                                .bind("class_number", requestedStudent.classNumber())
                                .executeAndReturnGeneratedKeys("id")
                )
                .mapTo(Long.class)
                .one();
    }

    public long updateStudent(Student requestedStudent) {
        return jdbi.inTransaction(handle ->
                        handle.createUpdate(create().locate("update_student"))
                                .bind("first_name", requestedStudent.firstName())
                                .bind("last_name", requestedStudent.lastName())
                                .bind("class_number", requestedStudent.classNumber())
                                .executeAndReturnGeneratedKeys("id")
                )
                .mapTo(Long.class)
                .one();
    }

    public void deleteStudent(long id) {
        jdbi.inTransaction(handle ->
                handle.execute(create().locate("delete_student"))
        );
    }

    public List<Teacher> teachers() {
        return jdbi.inTransaction(handle ->
                handle.createQuery(create().locate("select_teacher"))
                        .mapTo(Teacher.class)
                        .list()
        );
    }

    public Teacher getTeacher(long id) {
        return jdbi.inTransaction(handle ->
                handle.createQuery(create().locate("select_teacher_by_id"))
                        .bind("id", id)
                        .mapTo(Teacher.class)
                        .one()
        );
    }


    public long addTeacher(Teacher requestedTeacher) {
        return jdbi.inTransaction(handle ->
                        handle.createUpdate(create().locate("add_student"))
                                .bind("first_name", requestedTeacher.firstName())
                                .bind("last_name", requestedTeacher.lastName())
                                .executeAndReturnGeneratedKeys("id")
                )
                .mapTo(Long.class)
                .one();
    }

    public long updateTeacher(Teacher requestedTeacher) {
        return jdbi.inTransaction(handle ->
                        handle.createUpdate(create().locate("update_teacher"))
                                .bind("first_name", requestedTeacher.firstName())
                                .bind("last_name", requestedTeacher.lastName())
                                .executeAndReturnGeneratedKeys("id")
                )
                .mapTo(Long.class)
                .one();
    }

    public void deleteTeacher(long id) {
        jdbi.inTransaction(handle ->
                handle.execute(create().locate("delete_teacher"))
        );
    }

    public List<Profile> profiles() {
        return jdbi.inTransaction(handle ->
                handle.createQuery(create().locate("select_profile"))
                        .mapTo(Profile.class)
                        .list());
    }

    public Profile getProfile(long id) {
        return jdbi.inTransaction(handle ->
                handle.createQuery(create().locate("select_profile_by_id"))
                        .bind("id", id)
                        .mapTo(Profile.class)
                        .one()
        );
    }


    public long addProfile(Profile requestedProfile) {
        return jdbi.inTransaction(handle ->
                        handle.createUpdate(create().locate("add_profile"))
                                .bind("name", requestedProfile.name())
                                .bind("lesson_length", requestedProfile.lessonLength())
                                .bind("class_range", requestedProfile.classRange())
                                .bind("is_itn", requestedProfile.itn())
                                .executeAndReturnGeneratedKeys("id")
                )
                .mapTo(Long.class)
                .one();
    }

    public long updateProfile(Profile requestedProfile) {
        return jdbi.inTransaction(handle ->
                        handle.createUpdate(create().locate("update_profile"))
                                .bind("name", requestedProfile.name())
                                .bind("lesson_length", requestedProfile.lessonLength())
                                .bind("class_range", requestedProfile.classRange())
                                .bind("is_itn", requestedProfile.itn())
                                .executeAndReturnGeneratedKeys("id")
                )
                .mapTo(Long.class)
                .one();
    }

    public void deleteProfile(long id) {
        jdbi.inTransaction(handle ->
                handle.execute(create().locate("delete_profile"))
        );
    }
}
