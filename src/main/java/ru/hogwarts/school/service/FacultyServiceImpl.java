package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository repositoryOfFaculty;

    public FacultyServiceImpl(FacultyRepository repositoryOfFaculty) {
        this.repositoryOfFaculty = repositoryOfFaculty;
    }

    @Override
    public Faculty create(Faculty faculty) {
        return repositoryOfFaculty.save(faculty);
    }

    @Override
    public Faculty read(long id) {
        return repositoryOfFaculty.findById(id).
                orElseThrow(() -> new FacultyNotFoundException("Факультет с id" + id + "не найден в хранилище"));
    }

    @Override
    public Faculty update(Faculty faculty) {
        read(faculty.getId());
        return repositoryOfFaculty.save(faculty);
    }

    @Override
    public Faculty delete(long id) {
        Faculty faculty = read(id);
        repositoryOfFaculty.delete(faculty);
        return faculty;
    }

    @Override
    public Collection<Faculty> colorSorter(String color) {
        return repositoryOfFaculty.findByColor(color);
    }
    @Override
    public Collection<Faculty> findAllByNameIgnoreCaseOrColorIgnoreCase(String name, String color) {
        return repositoryOfFaculty.findAllByNameIgnoreCaseOrColorIgnoreCase(name,color);
    }
}
