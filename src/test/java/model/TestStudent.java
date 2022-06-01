package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestStudent {
    private final Student emptyStudent = new Student();
    private final Student testStudent = new Student(1L, "Henryk", "Sienkiewicz", 2);

    @Test
    public void getStudentId() {
        assertEquals(emptyStudent.getId(), 0);
        assertEquals(testStudent.getId(), 1L);
    }

    @Test
    public void getFirstName() {
        assertNull(emptyStudent.getFirstName());
        assertEquals(testStudent.getFirstName(), "Henryk");
    }
}
