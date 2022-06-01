package persistence;

import model.Student;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.testing.junit5.JdbiH2Extension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;


@ExtendWith(JdbiH2Extension.class)
public class TestStudentPersistence {
    private final StudentPersistence studentPersistence = new StudentPersistence();

    @Test
    public void testGetAll(Jdbi jdbi, Handle handle) {
        List<Student> students = handle.createQuery("SELECT * FROM student").mapTo(Student.class).list();
        assertEquals(students.size(), 0);
    }
}
