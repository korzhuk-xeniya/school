package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Student create(Student student) {
        return repository.save(student);
    }

    @Override
    public Student read(long id) {
        return (Student) repository.findAllById(id).
                orElseThrow(() -> new StudentNotFoundException("Студент с id " + id + " не найден в хранилище"));
    }

    @Override
    public Student update(Student student) {
        read(student.getId());
        return repository.save(student);
    }

    @Override
    public Student delete(long id) {
        Student student = read(id);
        repository.delete(student);
        return student;
    }

    @Override
    public Collection<Student> ageSorter(int age) {
        return repository.findAllByAge(age);
    }
    @Override
    public Collection<Student> findByAgeBetween(int minAge, int maxAge) {
        return repository.findByAgeBetween(minAge,maxAge);
    }
    @Override
    public Faculty readFacultyOfStudent(long studentId) {
        return read(studentId).getFaculty();
    }
    @Override
    public Collection<Student> readByFacultyId(long facultyId) {
        return repository.findByFaculty_id(facultyId);
    }
}
