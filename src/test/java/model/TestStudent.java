package model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestStudent {
    private final Student emptyStudent = new Student();
    private final Student testStudent = new Student(1L, "Henryk", "Sienkiewicz", 2);

    private final Lesson emptyLesson = new Lesson();

    @Test
    public void testGetStudentId() {
        assertEquals(emptyStudent.getId(), 0);
        assertEquals(testStudent.getId(), 1L);
    }

    @Test
    public void testGetFirstName() {
        assertNull(emptyStudent.getFirstName());
        assertEquals(testStudent.getFirstName(), "Henryk");
    }

    @Test
    public void testGetLastName() {
        assertNull(emptyStudent.getLastName());
        assertEquals(testStudent.getLastName(), "Sienkiewicz");
    }

    @Test
    public void testGetClassNumber() {
        assertEquals(emptyStudent.getClassNumber(), 0);
        assertEquals(testStudent.getClassNumber(), 2);
    }

    @Test
    public void testSetStudentId() {
        emptyStudent.setId(2L);
        assertEquals(emptyStudent.getId(), 2L);
    }

    @Test
    public void testSetFirstName() {
        emptyStudent.setFirstName("Adam");
        assertEquals(emptyStudent.getFirstName(), "Adam");
    }

    @Test
    public void testSetLastName() {
        emptyStudent.setLastName("Mickiewicz");
        assertEquals(emptyStudent.getLastName(), "Mickiewicz");
    }

    @Test
    public void testSetClassNumber() {
        emptyStudent.setClassNumber(3);
        assertEquals(emptyStudent.getClassNumber(), 3);
    }

    @Test
    public void testGetLesson() {
        List<Lesson> lessons = emptyStudent.getLessons();
        assertNull(lessons);
    }

    @Test
    public void testSetLesson() {
        emptyStudent.setLessons(List.of(emptyLesson));
        List<Lesson> lessons = emptyStudent.getLessons();
        assertEquals(lessons.size(), 1);
        assertEquals(lessons.get(0), emptyLesson);
    }
}
