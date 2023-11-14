package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Collection<Faculty> findByColor(String color);
    Collection<Faculty> findAllByNameIgnoreCaseOrColorIgnoreCase(String name, String color);

    Collection <Faculty> findAllById(Long id);
}
