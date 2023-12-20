package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    private boolean marker = false;

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
        return repository.findByAgeBetween(minAge, maxAge);
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
    public Collection<String> getFilteredByNameAndTernToUpperCase() {
        return repository.findAll().stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(s -> s.startsWith("A"))
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public Double getAllStudentAvgAge() {
        return repository.findAll()
                .stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0);
    }

    @Override
    public void getStudentNames() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            printName(3L);
            printName(4L);
        });
        thread1.setName("Thread 1");
        Thread thread2 = new Thread(() -> {
            printName(5L);
            printName(6L);
        });
        thread2.setName("Thread 2");
        thread1.start();
        thread2.start();
    }

    @Override
    public void getStudentNamesWait() {
        List<Student> allStudents = repository.findAll();
        System.out.println(allStudents.get(0).getName());
        System.out.println(allStudents.get(1).getName());
        Thread thread1 = new Thread(() -> {
            printNameSyncWait(2, 3, allStudents);
        });
        thread1.setName("Thread 1");
        Thread thread2 = new Thread(() -> {
            printNameSyncWait2(4, 5, allStudents);
        });
        thread2.setName("Thread 2");
        thread2.start();
        thread1.start();

    }

    private synchronized void printNameSyncWait2(int id1, int id2, List<Student> students) {
        while (!marker)
            try {
                wait();
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        System.out.println(students.get(id1).getName());
        System.out.println(students.get(id2).getName());
    }

    private synchronized void printNameSyncWait(int id1, int id2, List<Student> students) {
        while (marker)
            try {
                wait();
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        System.out.println(students.get(id1).getName());
        System.out.println(students.get(id2).getName());
        marker = true;
        notify();
    }

    @Override
    public void getStudentNamesSync() {
        Thread thread1 = new Thread(() -> {
            printNameSync(3L);
            printNameSync(4L);
        });
        Thread thread2 = new Thread(() -> {
            printNameSync(5L);
            printNameSync(6L);
        });
        printNameSync(1L);
        printNameSync(2L);
        thread1.start();
        thread2.start();
    }

    private synchronized void printNameSync(long id) {
        String studentName = repository.getById(id).getName();
        System.out.println(studentName);
    }

    private void printName(long id) {
        String studentName = repository.getById(id).getName();
        System.out.println((studentName));
    }
}
