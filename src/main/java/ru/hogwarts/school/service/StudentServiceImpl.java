package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Student create(Student student) {
        logger.info("Был вызван метод для создания студента", student);
        return repository.save(student);
    }

    @Override
    public Student read(long id) {
        logger.error("Был вызван метод для выбрасывания ошибки если студент не найден по id", id);
        return (Student) repository.findAllById(id).
                orElseThrow(() -> new StudentNotFoundException("Студент с id " + id + " не найден в хранилище"));
    }

    @Override
    public Student update(Student student) {
        logger.info("Был вызван метод для обновления студента", student);
        read(student.getId());
        return repository.save(student);
    }

    @Override
    public Student delete(long id) {
        logger.info("Был вызван метод для удаления студента по id", id);
        Student student = read(id);
        repository.delete(student);
        return student;
    }

    @Override
    public Collection<Student> ageSorter(int age) {
        logger.info("Был вызван метод для поиска студентов по возрасту", age);
        return repository.findByAge(age);
    }
    @Override
    public Collection<Student> findByAgeBetween(int minAge, int maxAge) {
        logger.info("Был вызван метод для поиска студентов по возрасту в заданном интервале", minAge, maxAge);
        return repository.findByAgeBetween(minAge,maxAge);
    }
    @Override
    public Faculty readFacultyOfStudent(long studentId) {
        logger.info("Был вызван метод для поиска факультета по id студента", studentId);
        return read(studentId).getFaculty();
    }
    @Override
    public Collection<Student> readByFacultyId(long facultyId) {
        logger.info("Был вызван метод для поиска всех студентов факультета по id факультета", facultyId);
        return repository.findByFaculty_id(facultyId);
    }
    @Override
    public Collection<String> getFilteredByNameAndTernToUpperCase(){
        return repository.findAll().stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(s -> s.startsWith("A"))
                .sorted()
                .collect(Collectors.toList());
    }
    @Override
    public  Double getAllStudentAvgAge() {
        return repository.findAll()
                .stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0);
    }
}
