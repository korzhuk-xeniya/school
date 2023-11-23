package ru.hogwarts.school.service;

//import org.apache.el.stream.Stream;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
    public Collection<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(String name, String color) {
        Stream combinedFaculties = Stream.of(repositoryOfFaculty.findByNameIgnoreCase(name),
                repositoryOfFaculty.findByColorIgnoreCase(color));
         Stream.of(repositoryOfFaculty.findByNameIgnoreCase(name), repositoryOfFaculty.findByColorIgnoreCase(color))
                .flatMap(x -> {
                    return x.stream();
                })
                .collect(Collectors.toList());

        List<Faculty> list = new ArrayList<Faculty>();
        Stream.of(repositoryOfFaculty.findByNameIgnoreCase(name),
                repositoryOfFaculty.findByColorIgnoreCase(color)).forEach(list::addAll);
        return list;

    }
}
