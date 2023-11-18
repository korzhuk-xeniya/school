package ru.hogwarts.school.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerRestTemplateTest {
    @Autowired
    TestRestTemplate restTemplate;
    @LocalServerPort
    int port;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    FacultyRepository facultyRepository;
    String baseUrl;
    private Student student = new Student(1L, "Olga", 23);

    @BeforeEach
    void beforeEach() {
        baseUrl = "http://localhost:" + port + "/student";
        studentRepository.deleteAll();
    }

    @Test
    void create_shouldReturnAddedStudentAndStatus200() {
        ResponseEntity<Student> result = restTemplate.postForEntity(baseUrl, student, Student.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(student, result.getBody());
    }

    @Test
    void read_shouldReturnStatus404() {
        ResponseEntity<String> result = restTemplate.getForEntity(baseUrl + "/" + student.getId(), String.class);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals("Студент с id " + student.getId() + " не найден в хранилище", result.getBody());
    }

    @Test
    void update_shouldReturnStudentAndStatus200() {
        Student saveStudent = studentRepository.save(student);
        ResponseEntity<Student> result = restTemplate.exchange(baseUrl,
                HttpMethod.PUT,
                new HttpEntity<>(saveStudent), Student.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(student, result.getBody());
    }

    @Test
    void delete_shouldReturnStatus200AndStudent() {
        Student saveStudent = studentRepository.save(student);
        ResponseEntity<Student> result = restTemplate.exchange(baseUrl + "/" + saveStudent.getId(),
                HttpMethod.DELETE,
                new HttpEntity<>(saveStudent), Student.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(saveStudent, result.getBody());
    }

    @Test
    void ageSorter_shouldReturnStudentsCollectionAndStatus200() {
        Student student1 = studentRepository.save(student);
        Student saveStudent2 = new Student(2L, "Harry", 15);
        Student student2 = studentRepository.save(saveStudent2);
        Student saveStudent3 = new Student(3L, "Ron", 15);
        Student student3 = studentRepository.save(saveStudent3);
        ResponseEntity<List<Student>> result = restTemplate.exchange(baseUrl + "/age/" + student2.getAge(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(List.of(student2,student3), result.getBody());
    }
    @Test
    void findByAgeBetween_shouldReturnStudentsCollectionsWithAgeBetweenMinAgeAndMaxAge() {
        Student student1 = studentRepository.save(student);
        Student saveStudent2 = new Student(2L, "Harry", 15);
        Student student2 = studentRepository.save(saveStudent2);
        Student saveStudent3 = new Student(3L, "Ron", 11);
        Student student3 = studentRepository.save(saveStudent3);
        ResponseEntity<List<Student>> result = restTemplate.exchange(baseUrl + "?minAge=" + student3.getAge() + "&maxAge=" + student2.getAge(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(List.of(student2,student3), result.getBody());
    }
//    @Test
//    void readFacultyOfStudent_shouldReturnFacultyByStudentId() {
//        Student student1 = studentRepository.save(student);
//        Faculty saveFaculty = new Faculty(1L, "Griffindor", "yellow");
//        Faculty faculty = facultyRepository.save(saveFaculty);
//        student.setFaculty(faculty);
//        ResponseEntity<Faculty> result = restTemplate.getForEntity(baseUrl + "/faculty/" + student.getId(),faculty, Faculty.class);
////        ResponseEntity<Faculty> result = restTemplate.exchange(baseUrl + "/faculty/"+ student1.getId(),
////                HttpMethod.GET,
////                faculty,
////                Faculty.class);
//        assertEquals(HttpStatus.OK, result.getStatusCode());
//        assertEquals(faculty, result.getBody());

    }
//    @Test
//    void readByFacultyId_shouldReturnStudentsCollectionByFacultyId() {
//        Student student1 = studentRepository.save(student);
//        Student saveStudent2 = new Student(2L, "Harry", 15);
//        Student student2 = studentRepository.save(saveStudent2);
//        Student saveStudent3 = new Student(3L, "Ron", 11);
//        Student student3 = studentRepository.save(saveStudent3);
//        Faculty faculty = new Faculty(1L, "Griffindor", "yellow");
//        Faculty faculty2 = new Faculty(2L, "Slytherin", "green");
//        student.setFaculty(faculty);
//        student2.setFaculty(faculty);
//        student3.setFaculty(faculty2);
//        restTemplate.getForEntity(baseUrl + "/faculty/"+);
//
//    }
}
