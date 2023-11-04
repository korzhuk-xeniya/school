package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school.exception.FacultyAlreadyExsitsException;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.exception.StudendAlreadyExsitsException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class FacultyServiceImplTest {
    private FacultyServiceImpl underTest = new FacultyServiceImpl();
    private Faculty faculty = new Faculty(1L,"Griffindor","yellow");
    private Faculty faculty2 = new Faculty(2L,"Slytherin","green");
    private Faculty faculty3 = new Faculty(3L,"Hufflepuf", "yellow");

    @Test
    void create_shouldReturnAddedFaculty() {
        Faculty result = underTest.create(faculty);

        assertEquals(faculty, result);
    }
    @Test
    void create_shouldThrowExceptionWhenFacultyAlreadyExsists() {
        underTest.create(faculty);
        assertThrows(FacultyAlreadyExsitsException.class, () -> underTest.create(faculty));
    }
    @Test
    void read_shouldReturnFacultyById() {
        underTest.create(faculty);
        Faculty result = underTest.read(faculty.getId());

        assertEquals(faculty, result);
    }

    @Test
    void read_shouldThrowExceptionWhenFacultyWithIdNotFound() {
        assertThrows(FacultyNotFoundException.class, () -> underTest.read(faculty.getId()));
    }


    @Test
    void update_ShouldThrowExceptionWhenFacultyWithIdNotFound() {
        assertThrows(FacultyNotFoundException.class, () -> underTest.update(faculty));
    }

    @Test
    void delete_shouldReturnDeletedFaculty() {
        underTest.create(faculty);
        underTest.create(faculty2);
        long id = faculty.getId();
        Faculty result = underTest.delete(id);

        assertEquals(faculty, result);
    }

    @Test
    void delete_shouldThrowExceptionWhenCollectionNotContainsFaculty() {
        assertThrows(FacultyNotFoundException.class, () -> underTest.delete(faculty.getId()));
    }


    @Test
    void facultySorter_shouldSortedByColor() {
        underTest.create(faculty);
        underTest.create(faculty2);
        underTest.create(faculty3);
        Collection<Faculty> facultiesSortedByColor = new ArrayList<>(Arrays.asList(faculty, faculty3));
        String color = "yellow";
        Collection <Faculty> result = underTest.facultySorter(color);

        assertEquals(facultiesSortedByColor, result);
    }
}