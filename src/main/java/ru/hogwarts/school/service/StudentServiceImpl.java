package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudendAlreadyExsitsException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final Map<Long, Student> repository = new HashMap<>();
//    private Long idCounter = 0L;
    @Override
    public Student create(Student student) {
        if (repository.containsValue(student)) {
            throw new StudendAlreadyExsitsException("Студент" + student + "уже есть в хранилище");
        }
//        long id = ++idCounter;
//        student.setId(id);
        repository.put(student.getId(), student);
        return student;
    }
    @Override
    public Student read(long id) {
        Student student = repository.get(id);
        if (student == null) {
            throw new StudentNotFoundException("Студент с id" + id + "не найден в хранилище");
        }
        return student;
    }
    @Override
    public Student update(Student student) {
        if (!repository.containsKey(student.getId())) {
            throw new StudentNotFoundException("Студент с id" + student.getId() + "не найден в хранилище");
        }
        repository.put(student.getId(), student);
        return student;
    }
    @Override
    public Student delete(long id) {
        Student student = repository.remove(id);
        if (student == null) {
            throw  new StudentNotFoundException("Студент с id" + id + "не найден в хранилище");
        }
        return student;
    }

    @Override
    public Collection<Student> ageSorter(int age) {
        return repository.values().stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toUnmodifiableList());
    }
}
