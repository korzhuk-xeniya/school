package ru.hogwarts.school.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import java.io.File;
import java.io.IOException;

public interface AvatarService {
    Avatar findAvatar(Long studentId);

    void uploadAvatar(Long studentId, MultipartFile file) throws IOException;

    Avatar readFromDB(Long id);

    File readFromFile(long id);
}
