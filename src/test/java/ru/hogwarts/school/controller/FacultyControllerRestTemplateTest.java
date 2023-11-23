package ru.hogwarts.school.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerRestTemplateTest {
    @Autowired
    TestRestTemplate restTemplate;
    @LocalServerPort
    int port;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    FacultyRepository facultyRepository;
    String baseUrl;
    private Faculty faculty = new Faculty(1L, "Griffindor", "yellow");
    @BeforeEach
    void beforeEach() {
        baseUrl = "http://localhost:" + port + "/faculty";
        facultyRepository.deleteAll();
    }
    @Test
    void create_shouldReturnFacultyAndStatus200() {

    }
    @Test
    void read_shouldReturnStatus404() {

    }
    @Test
    void update_shouldReturnFacultyAndStatus200() {

    }
    @Test
    void delete_shouldReturnStatus200AndFaculty() {

    }
    @Test
    void facultySorter_shouldReturnFacultiesCollectionAndStatus200() {

    }
}
