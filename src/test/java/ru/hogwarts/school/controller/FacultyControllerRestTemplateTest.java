package ru.hogwarts.school.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;

import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        ResponseEntity<Faculty> result = restTemplate.postForEntity(baseUrl, faculty, Faculty.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(faculty, result.getBody());
    }

    @Test
    void read_shouldReturnStatus404() {
        ResponseEntity<String> result = restTemplate.getForEntity(baseUrl + "/" + faculty.getId(), String.class);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals("Факультет с id " + faculty.getId() + " не найден в хранилище", result.getBody());

    }

    @Test
    void update_shouldReturnFacultyAndStatus200() {
        Faculty saveFaculty = facultyRepository.save(faculty);
        ResponseEntity<Faculty> result = restTemplate.exchange(baseUrl,
                HttpMethod.PUT,
                new HttpEntity<>(saveFaculty), Faculty.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(faculty, result.getBody());
    }

    @Test
    void delete_shouldReturnStatus200AndFaculty() {
        Faculty saveFaculty = facultyRepository.save(faculty);
        ResponseEntity<Faculty> result = restTemplate.exchange(baseUrl + "/" + saveFaculty.getId(),
                HttpMethod.DELETE,
                new HttpEntity<>(saveFaculty), Faculty.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(saveFaculty, result.getBody());
    }

    @Test
    void facultySorter_shouldReturnFacultiesCollectionAndStatus200() {
        Faculty saveFaculty = facultyRepository.save(faculty);
        Faculty saveFaculty2 = new Faculty(2L, "Slytherin", "green");
        Faculty faculty2 = facultyRepository.save(saveFaculty2);
        Faculty saveFaculty3 = new Faculty(3L, "Hufflepuf", "yellow");
        Faculty faculty3 = facultyRepository.save(saveFaculty3);
        ResponseEntity<List<Faculty>> result = restTemplate.exchange(baseUrl + "/color/" + saveFaculty.getColor(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(List.of(saveFaculty, faculty3), result.getBody());
    }

}
