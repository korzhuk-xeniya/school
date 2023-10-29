package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyAlreadyExsitsException;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final Map<Long, Faculty> repositoryOfFaculty = new HashMap<>();
    private Long idCounter = 0L;
    @Override
    public Faculty create(Faculty faculty) {
        if (repositoryOfFaculty.containsValue(faculty)) {
            throw new FacultyAlreadyExsitsException("Факультет" + faculty + "уже есть в хранилище");
        }
        long id = ++idCounter;
        faculty.setId(id);
        repositoryOfFaculty.put(id, faculty);
        return faculty;
    }
    @Override
    public Faculty read(long id) {
        Faculty faculty = repositoryOfFaculty.get(id);
        if (faculty == null) {
            throw new FacultyNotFoundException("Факультет с id" + id + "не найден в хранилище");
        }
        return faculty;
    }
    @Override
    public Faculty update(Faculty faculty) {
        if (!repositoryOfFaculty.containsKey(faculty.getId())) {
            throw new FacultyNotFoundException("Факультет с id" + faculty.getId() + "не найден в хранилище");
        }
        repositoryOfFaculty.put(faculty.getId(), faculty);
        return faculty;
    }
    @Override
    public Faculty delete(long id) {
        Faculty faculty = repositoryOfFaculty.remove(id);
        if (faculty == null) {
            throw new FacultyNotFoundException("Факультет с id" + id + "не найден в хранилище");
        }
        return faculty;
    }

    @Override
    public Collection<Faculty> facultySorter(String color) {
        return repositoryOfFaculty.values().stream()
                .filter(faculty -> faculty.getColor() == color)
                .collect(Collectors.toUnmodifiableList());
    }
}