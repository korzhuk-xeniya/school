package ru.hogwarts.school.service;

//import org.apache.el.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Service
public class FacultyServiceImpl implements FacultyService {
    private final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);
    private final FacultyRepository repositoryOfFaculty;

    public FacultyServiceImpl(FacultyRepository repositoryOfFaculty) {
        this.repositoryOfFaculty = repositoryOfFaculty;
    }

    @Override
    public Faculty create(Faculty faculty) {
        logger.info("Был вызван метод для создания факультета", faculty);
        return repositoryOfFaculty.save(faculty);
    }

    @Override
    public Faculty read(long id) {
        logger.error("Был вызван метод для выбрасывания ошибки если факультет не найден по id", id);
        return repositoryOfFaculty.findById(id).
                orElseThrow(() -> new FacultyNotFoundException("Факультет с id " + id + " не найден в хранилище"));
    }

    @Override
    public Faculty update(Faculty faculty) {
        logger.info("Был вызван метод для обновления факультета", faculty);
        read(faculty.getId());
        return repositoryOfFaculty.save(faculty);
    }

    @Override
    public Faculty delete(long id) {
        logger.info("Был вызван метод для удаления факультета по id", id);
        Faculty faculty = read(id);
        repositoryOfFaculty.delete(faculty);
        return faculty;
    }

    @Override
    public Collection<Faculty> colorSorter(String color) {
        logger.info("Был вызван метод для отбора факультетов по заданному цвету", color);
        return repositoryOfFaculty.findByColor(color);
    }
    @Override
    public Collection<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(String name, String color) {
        logger.info("Был вызван метод для поиска факультетов по названию или цвету игнорируя регистр", name, color);
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
    @Override
    public ResponseEntity<String> getFacultyWithMaxLength(){
        logger.info("Был вызван метод для поиска факультета с самым длинным названием");

                Optional<String> maxFacultyName =  repositoryOfFaculty
                        .findAll()
                        .stream()
                        .map(Faculty::getName)
                        .max(Comparator.comparing(String::length));
        if (maxFacultyName.isEmpty()) {
            logger.error("В хранилоще нет факультетов");
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(maxFacultyName.get());
        }



    }
}
