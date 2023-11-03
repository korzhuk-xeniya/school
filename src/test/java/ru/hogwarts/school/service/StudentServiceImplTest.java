package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Student;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceImplTest {
    private  StudentServiceImpl underTest = new  StudentServiceImpl();
    private Student student = new Student(1L,"Olga", 23);
    private Student student2 = new Student(2L,"Harry", 15);
    private Student student3 = new Student(3L,"Ron", 15);
    @Test
    void create() {
    }

    @Test
    void read() {
    }

    @Test
    void update() {
    }

//    @Test
//    void delete_shouldDeleteStudentAndReturnDeletedStudent() {
//        underTest.create(student);
//        underTest.create(student2);
//
//        Student result = underTest.delete(student.getId());
//
//        assertNull(underTest.read(student.getId()));
//        assertEquals(student, result);
//    }

    @Test
    void ageSorter() {
    }
}