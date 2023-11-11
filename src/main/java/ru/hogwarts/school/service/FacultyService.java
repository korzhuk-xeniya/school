package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface FacultyService {
    Faculty create(Faculty faculty);

    Faculty read(long id);

    Faculty update(Faculty faculty);

    Faculty delete(long id);

    Collection<Faculty> colorSorter(String color);

    Collection<Faculty> findAllByNameIgnoreCaseOrColorIgnoreCase(String name, String color);
}
