package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestStudent {

    private final Student testStudent = new Student(1L, "Henryk", "Sienkiewicz", 2);

    @Test
    public void getStudentId() {
        Student student = new Student();
        assertEquals(student.getId(), 0);
        assertEquals(testStudent.getId(), 1L);
    }

    @Test
    public void getFirstName() {
        Student student = new Student();
        assertNull(student.getFirstName());
        assertEquals(testStudent.getFirstName(), "Henryk");
    }


}
