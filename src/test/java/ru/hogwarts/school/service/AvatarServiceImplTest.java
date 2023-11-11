package ru.hogwarts.school.service;

import nonapi.io.github.classgraph.utils.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exception.AvatarNotFoundException;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AvatarServiceImplTest {
    StudentService studentService = mock(StudentService.class);
    AvatarRepository avatarRepository = mock(AvatarRepository.class);
    String avatarsDir = "./src/test/resources/avatar";
    AvatarServiceImpl avatarService = new AvatarServiceImpl(
            studentService, avatarRepository, avatarsDir);
    private Student student = new Student(10L, "Harry", 11);
    private String fileName = "1.pdf";
    private MultipartFile file = new MockMultipartFile(fileName, fileName,
            "application/pdf", new byte[]{});

    @Test
    void findAvatar_shouldReturnAvatarByStudentId() {
        when(avatarRepository.findByStudentId(student.getId()))
                .thenReturn(Optional.empty());
    }

    @Test
    void uploadAvatar_avatarSavedToDbAndDirectory() throws IOException {
//        String fileName = "1.pdf";
//        MultipartFile file = new MockMultipartFile(fileName, fileName,
//                "application/pdf", new byte[]{});
        when(studentService.read(student.getId())).thenReturn(student);

        avatarService.uploadAvatar(student.getId(), file);

        verify(avatarRepository, times(1)).save(any());
        assertTrue(FileUtils.canRead(new File(avatarsDir
                + "/" + student.getId() + "." + fileName
                .substring(fileName.lastIndexOf(".") + 1))));
    }

    @Test
    void readFromDB_shouldReturnAvatarById() throws IOException {

        Avatar avatar = avatarService.findAvatar(student.getId());
        when(avatarRepository.findById(avatarService.findAvatar(student.getId()).getId()))
                .thenReturn(Optional.of(avatar));

        Avatar result = avatarService.readFromDB(avatar.getId());

        assertEquals(avatar, result);
    }

    @Test
    void readFromDB_shouldThrowExceptionWhenAvatarWithIdNotFound() {
        when(avatarRepository.findById(avatarService.findAvatar(student.getId()).getId()))
                .thenReturn(Optional.empty());
        assertThrows(AvatarNotFoundException.class,
                () -> avatarService.readFromDB(avatarService.findAvatar(student.getId()).getId()));
    }

//    @Test
//    void readFromFile() {
//        Avatar avatar = avatarService.findAvatar(student.getId());
//        Path path = Path.of(avatar.getFilePath());
//        when(avatarRepository.findById(avatarService.findAvatar(student.getId()).getId()))
//                .thenReturn(Optional.of(avatar));
//        File file1 = new File(path.toString());
//        File result = avatarService.readFromFile(avatar.getId());
//
//        assertEquals(file1, result);
//    }
}