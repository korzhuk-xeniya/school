package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerWebMvcTest {
    @MockBean
    StudentRepository studentRepository;
    @SpyBean
    StudentServiceImpl studentService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    private Student student = new Student(1L, "Olga", 23);
    private Student student2 = new Student(2L, "Harry", 15);
    private Student student3 = new Student(3L, "Ron", 11);

    private Faculty faculty = new Faculty(1L, "Griffindor", "yellow");
    private Faculty faculty2 = new Faculty(2L, "Slytherin", "green");

    @Test
    void create_shouldReturnStudentAndStatus200() throws Exception {
        when(studentRepository.save(student)).thenReturn(student);
        mockMvc.perform(post("/student").content(objectMapper.writeValueAsString(student))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(student));
    }

    @Test
    void read_shouldReturnStatus404() throws Exception {
        when(studentRepository.findById(student.getId())).thenReturn(Optional.empty());
        mockMvc.perform(get("/student/" + student.getId()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$")
                        .value("Студент с id " + student.getId() + " не найден в хранилище"));
    }

    @Test
    void update_shouldReturnStudentAndStatus200() throws Exception {
        when(studentRepository.save(student)).thenReturn(student);
        when(studentRepository.findAllById(student.getId())).thenReturn(Optional.of(student));

        mockMvc.perform(put("/student").content(objectMapper.writeValueAsString(student))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(student));
    }

    @Test
    void delete_shouldReturnStatus200AndStudent() throws Exception {
        when(studentRepository.findAllById(student.getId())).thenReturn(Optional.of(student));

        mockMvc.perform(delete("/student/" + student.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(student));
    }

    @Test
    void ageSorter_shouldReturnStudentsCollectionAndStatus200() throws Exception {
        when(studentRepository.findByAge(student.getAge())).thenReturn(List.of(student, student2));
        mockMvc.perform(get("/student/age/" + student.getAge()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]").value(student))
                .andExpect(jsonPath("$.[1]").value(student2));
    }

    @Test
    void findByAgeBetween_shouldReturnStudentsCollectionsWithAgeBetweenMinAgeAndMaxAge() throws Exception {

        when(studentRepository.findByAgeBetween(student3.getAge(), student2.getAge()))
                .thenReturn(new ArrayList<>(Arrays.asList(student2, student3)));
        mockMvc.perform(get("/student?minAge&maxAge" + student3.getAge() + student2.getAge()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]").value(student2))
                .andExpect(jsonPath("$.[1]").value(student3));
    }

    @Test
    void readFacultyOfStudent_shouldReturnFacultyByStudentId() throws Exception {
        student.setFaculty(faculty);
        when(studentRepository.findAllById(student.getId())).thenReturn(Optional.of(student));
        mockMvc.perform(get("/student/faculty/" + student.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(faculty));
    }

    @Test
    void readByFacultyId_shouldReturnStudentsCollectionByFacultyId() throws Exception {
        student.setFaculty(faculty);
        student2.setFaculty(faculty);
        student3.setFaculty(faculty2);
        when(studentRepository.findByFaculty_id(faculty.getId()))
                .thenReturn( new ArrayList<>(Arrays.asList(student, student2)));
        mockMvc.perform(get("/student/studentsOfFaculty/" + faculty.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]").value(student))
                .andExpect(jsonPath("$.[1]").value(student2));
    }
}
