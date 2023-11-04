package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.exception.FacultyAlreadyExsitsException;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FacultyServiceImplTest {
    //    private FacultyServiceImpl underTest = new FacultyServiceImpl();
    @Mock
    FacultyRepository facultyRepository;
    @InjectMocks
    FacultyServiceImpl facultyService;
    private Faculty faculty = new Faculty(1L, "Griffindor", "yellow");
    private Faculty faculty2 = new Faculty(2L, "Slytherin", "green");
    private Faculty faculty3 = new Faculty(3L, "Hufflepuf", "yellow");

    @Test
    void create_shouldReturnAddedFaculty() {
        when(facultyRepository.save(faculty)).thenReturn(faculty);
        Faculty result = facultyService.create(faculty);

        assertEquals(faculty, result);
    }

    @Test
    void read_shouldReturnFacultyById() {
        when(facultyRepository.findById(faculty.getId())).thenReturn(Optional.of(faculty));
        facultyService.create(faculty);
        Faculty result = facultyService.read(faculty.getId());

        assertEquals(faculty, result);
    }

    @Test
    void read_shouldThrowExceptionWhenFacultyWithIdNotFound() {
        when(facultyRepository.findById(faculty.getId())).thenReturn(Optional.empty());
        assertThrows(FacultyNotFoundException.class, () -> facultyService.read(faculty.getId()));
    }




//    @Test
//    void delete_shouldReturnDeletedFaculty() {
//        facultyService.create(faculty);
//        facultyService.create(faculty2);
//        long id = faculty.getId();
//        Faculty result = facultyService.delete(id);
//
//        assertEquals(faculty, result);
//    }

//    @Test
//    void facultySorter_shouldSortedByColor() {
//        facultyService.create(faculty);
//        facultyService.create(faculty2);
//        facultyService.create(faculty3);
//        Collection<Faculty> facultiesSortedByColor = new ArrayList<>(Arrays.asList(faculty, faculty3));
//        String color = "yellow";
//        Collection<Faculty> result = facultyService.colorSorter(color);
//
//        assertEquals(facultiesSortedByColor, result);
//    }
}