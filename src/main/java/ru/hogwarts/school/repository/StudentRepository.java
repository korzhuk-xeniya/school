package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Object> findAllById(long id);
    Collection<Student> findByAge(int age);
    Collection<Student> findByAgeBetween(int minAge, int maxAge);
    Collection<Student> findByFaculty_id(long facultyId);
    @Query(value = "SELECT COUNT(*)FROM student", nativeQuery = true)
    Integer getCountOfAllStudents();
    @Query(value = "SELECT AVG(*)FROM student", nativeQuery = true)
    Double getAverageAgeOfStudents();
    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    Double getLastFiveStudents();

}
