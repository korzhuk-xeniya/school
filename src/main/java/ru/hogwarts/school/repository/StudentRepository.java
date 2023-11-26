package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Object> findAllById(long id);
    Collection<Student> findByAge(int age);
    Collection<Student> findByAgeBetween(int minAge, int maxAge);
    Collection<Student> findByFaculty_id(long facultyId);
}
