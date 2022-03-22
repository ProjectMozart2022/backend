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

//    public List<Student> students() {
//        return jdbi.inTransaction(handle ->
//                handle
//                        .createQuery(create().locate("select_student"))
//                        .mapTo(Student.class)
//                        .list());
//    }

    public List<Student> students() {
        jdbi.withHandle(handle ->
                handle.createQuery(create().locate("select_student"))).mapTo(Student.class).forEach(student -> System.out.println(student.classNumber()));
        return Collections.singletonList(new Student(1, "antoni", "karwowsky", 3));
    }

    public List<Teacher> teachers() {
        return jdbi.inTransaction(handle -> handle.createQuery(create().locate("select_teacher"))
                .mapTo(Teacher.class).stream().toList());
    }

    public List<Profile> profiles() {
        return jdbi.inTransaction(handle -> handle.createQuery(create().locate("select_profile"))
                .mapTo(Profile.class).stream().toList());
    }
}
