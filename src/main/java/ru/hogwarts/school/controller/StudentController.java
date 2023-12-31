package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.awt.*;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student create(@RequestBody Student student) {
        return studentService.create(student);
    }

    @GetMapping("/{id}")
    public Student read(@PathVariable long id) {
        return studentService.read(id);
    }

    @PutMapping
    public Student update(@RequestBody Student student) {
        return studentService.update(student);
    }

    @DeleteMapping("/{id}")
    public Student delete(@PathVariable long id) {
        return studentService.delete(id);
    }

    @GetMapping("/age/{age}")
    public Collection<Student> ageSorter(@PathVariable int age) {
        return studentService.ageSorter(age);
    }

    @GetMapping
    public Collection<Student> findByAgeBetween(@RequestParam int minAge, @RequestParam int maxAge) {
        return studentService.findByAgeBetween(minAge, maxAge);
    }

    @GetMapping("/faculty/{studentId}")
    public Faculty readFacultyOfStudent(@PathVariable long studentId) {
        return studentService.readFacultyOfStudent(studentId);
    }

    @GetMapping("/studentsOfFaculty/{facultyId}")
    public Collection<Student> readByFacultyId(@PathVariable long facultyId) {
        return studentService.readByFacultyId(facultyId);
    }
    @GetMapping("/filteredbyname")
    public ResponseEntity<Collection<String>> getAllStudentsWithAName() {
        Collection <String> stringCollection = studentService.getFilteredByNameAndTernToUpperCase();
        if (stringCollection.size() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(stringCollection);
    }
    @GetMapping("/getallstudentsavgage")
    public  Double getAllStudentsAvgAge () {
        return studentService.getAllStudentAvgAge();
    }
    @GetMapping("parallel-thread")
    public void getNames() throws InterruptedException {
        studentService.getStudentNames();
    }
    @GetMapping("sync-thread")
    public void getNamesSync() throws InterruptedException {
        studentService.getStudentNamesSync();
    }
    @GetMapping("sync-thread2")
    public void getNamesSyncWait() throws InterruptedException {
        studentService.getStudentNamesWait();
    }
}
