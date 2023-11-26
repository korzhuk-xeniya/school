package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static java.util.Optional.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {
    @Mock
    StudentRepository repository;
    @InjectMocks
    StudentServiceImpl service;
    //    private StudentServiceImpl underTest = new StudentServiceImpl();
    private Student student = new Student(1L, "Olga", 23);
    private Student student2 = new Student(2L, "Harry", 15);
    private Student student3 = new Student(3L, "Ron", 15);
    private Faculty faculty = new Faculty(1L, "Griffindor", "yellow");
    private Faculty faculty2 = new Faculty(2L, "Slytherin", "green");

    @Test
    void create_shouldReturnAddedStudent() {
        when(repository.save(student)).thenReturn(student);
        Student result = service.create(student);

        assertEquals(student, result);
    }

    @Test
    void read_shouldReturnStudentById() {
        when(repository.findAllById(student.getId())).thenReturn(Optional.of(student));

        Student result = service.read(student.getId());

        assertEquals(student, result);
    }

    @Test
    void read_shouldThrowExceptionWhenStudentWithIdNotFound() {
        when(repository.findAllById(student.getId())).thenReturn(empty());

        assertThrows(StudentNotFoundException.class, () -> service.read(student.getId()));
    }


    @Test
    void update_ShouldUpdateAndReturnUpdateStudent() {
        when(repository.findAllById(student.getId())).thenReturn(Optional.of(student));
        when(repository.save(student)).thenReturn(student);
        Student result = service.update(student);

        assertEquals(student, result);
    }

    @Test
    void delete_shouldReturnDeletedStudent() {
        when(repository.findAllById(student.getId())).thenReturn(Optional.of(student));
        service.create(student);


        Student result = service.delete(student.getId());

        assertEquals(student, result);
    }


    @Test
    void ageSorter_shouldSortedByAge() {
       service.create(student);
        service.create(student2);
        service.create(student3);
        Collection <Student> studentsSortedByAge = new ArrayList<>(Arrays.asList(student2, student3));
        int age = 15;
        when(repository.findByAge(age)).thenReturn( new ArrayList<>(Arrays.asList(student2, student3)));
        Collection <Student> result = service.ageSorter(age);

        assertEquals(studentsSortedByAge, result);
    }

    @Test
    void findByAgeBetween_shouldReturnStudentsCollectionsWithAgeBetweenMinAgeAndMaxAge() {
        int ageMin = 11;
        int ageMax = 18;
        service.create(student);
        service.create(student2);
        service.create(student3);
        Collection <Student> studentsSortedByAgeBetween = new ArrayList<>(Arrays.asList(student2, student3));
        when(repository.findByAgeBetween(ageMin,ageMax)).thenReturn( new ArrayList<>(Arrays.asList(student2, student3)));
        Collection <Student> result = service.findByAgeBetween(ageMin, ageMax);

        assertEquals(studentsSortedByAgeBetween, result);
    }

    @Test
    void readFacultyOfStudent_shouldReturnFacultyByStudentId() {
    student.setFaculty(faculty);
    when(repository.findAllById(student.getId())).thenReturn(Optional.of(student));
    Faculty result = service.readFacultyOfStudent(student.getId());

    assertEquals(faculty, result);

    }

    @Test
    void readByFacultyId_shouldReturnStudentsCollectionByFacultyId() {

        service.create(student);
        student.setFaculty(faculty);
        service.create(student2);
        student2.setFaculty(faculty);
        service.create(student3);
        student3.setFaculty(faculty2);
        Long facultyId = 1L;
        Collection <Student> studentsSortedByFaulty = new ArrayList<>(Arrays.asList(student, student2));
        when(repository.findByFaculty_id(facultyId)).thenReturn( new ArrayList<>(Arrays.asList(student, student2)));
        Collection <Student> result = service.readByFacultyId(facultyId);

        assertEquals(studentsSortedByFaulty, result);
    }
}