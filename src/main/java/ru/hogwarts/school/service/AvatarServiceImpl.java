package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exception.AvatarNotFoundException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;


import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarServiceImpl implements AvatarService {
    private final Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);
    private String avatarsDir;
    private final StudentService studentService;
    private final AvatarRepository avatarRepository;

    public AvatarServiceImpl(StudentService studentService, AvatarRepository avatarRepository,
                             @Value("${path.to.avatars.folder}") String avatarsDir) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
        this.avatarsDir = avatarsDir;
    }

    @Override
    public Avatar findAvatar(Long studentId) {
        logger.info("Был вызван метод для поиска аватара по id студента", studentId);
        return avatarRepository.findByStudentId(studentId)
                .orElse(new Avatar());
    }

    @Override
    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        logger.info("Был вызван метод для загрузки аватара студента в файл и базу данных", studentId);
        Student student = studentService.read(studentId);
        Path filePath = saveToFile(student, file);
        saveToDB(filePath, file, student);
    }

    private Path saveToFile(Student student, MultipartFile file) throws IOException {
        logger.info("Был вызван метод для загрузки аватара студента в файл ", student);
        Path filePath = Path.of(avatarsDir, student.getId() + "." + getExtensions(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        return filePath;
    }

    private String getExtensions(String fileName) {
        logger.info("Был вызван метод для получения расширения файла аватара", fileName);
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private void saveToDB(Path filePath, MultipartFile file, Student student) throws IOException {
        logger.info("Был вызван метод для загрузки аватара студента в базу данных", filePath, file, student);
        Avatar avatar = findAvatar(student.getId());
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());

        avatarRepository.save(avatar);
        logger.debug("Аватар был загружен в файл", student);
    }
    @Override
    public Avatar readFromDB(Long id) {
        logger.error("Был вызван метод для выбрасывания ошибки если аватар не найден по id в базе данных", id);
        return avatarRepository.findById(id).orElseThrow(() -> new AvatarNotFoundException("Аватар не найден"));
    }
    @Override
    public File readFromFile(long id) {
        logger.info("Был вызван метод для поиска аватара по id в файле", id);
        Avatar avatar = readFromDB(id);
        Path path = Path.of(avatar.getFilePath());

        return new File(path.toString());
    }

    @Override
    public Page<Avatar> getAllAvatars(Integer pageNo, Integer pageSize) {
        logger.info("Был вызван метод для получения всех аватарок");
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return avatarRepository.findAll(paging);
    }

}
