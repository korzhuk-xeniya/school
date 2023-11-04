package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school.exception.StudendAlreadyExsitsException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceImplTest {
    private StudentServiceImpl underTest = new StudentServiceImpl();
    private Student student = new Student(1L,"Olga", 23);
    private Student student2 = new Student(2L,"Harry", 15);
    private Student student3 = new Student(3L,"Ron", 15);

    @Test
    void create_shouldReturnAddedStudent() {
        Student result = underTest.create(student);

        assertEquals(student, result);
    }

    @Test
    void create_shouldThrowExceptionWhenStudentAlreadyExsists() {
        underTest.create(student);
        assertThrows(StudendAlreadyExsitsException.class, () -> underTest.create(student));
    }

    @Test
    void read_shouldReturnStudentById() {
        underTest.create(student);
        Student result = underTest.read(student.getId());

        assertEquals(student, result);
    }

    @Test
    void read_shouldThrowExceptionWhenStudentWithIdNotFound() {
        assertThrows(StudentNotFoundException.class, () -> underTest.read(student.getId()));
    }

    @Test
    void update_ShouldThrowExceptionWhenStudentWithIdNotFound() {
        assertThrows(StudentNotFoundException.class, () -> underTest.update(student));
    }
//    @Test
//    void update_ShouldUpdateAndReturnUpdateStudent() {
//        underTest.create(student);
//        student = new Student("Olga", 36);
//        Student result = underTest.update(student2);
//
//        assertEquals(student2, result);
//
//    }
    @Test
    void delete_shouldReturnDeletedStudent() {
        underTest.create(student);
        underTest.create(student2);
        long id = student.getId();
        Student result = underTest.delete(student.getId());

        assertEquals(student, result);
    }

    @Test
    void delete_shouldThrowExceptionWhenCollectionNotContainsStudent() {
        assertThrows(StudentNotFoundException.class, () -> underTest.delete(student.getId()));
    }

    @Test
    void ageSorter_shouldSortedByAge() {
        underTest.create(student);
        underTest.create(student2);
        underTest.create(student3);
        Collection <Student> studentsSortedByAge = new ArrayList<>(Arrays.asList(student2, student3));
        int age = 15;
        Collection <Student> result = underTest.ageSorter(age);

        assertEquals(studentsSortedByAge, result);
    }
}