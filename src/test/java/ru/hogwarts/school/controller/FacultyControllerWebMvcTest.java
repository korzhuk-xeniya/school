package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.FacultyServiceImpl;

import javax.annotation.ManagedBean;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
public class FacultyControllerWebMvcTest {
    @MockBean
    FacultyRepository facultyRepository;
    @SpyBean
    FacultyServiceImpl facultyService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    private Faculty faculty = new Faculty(1L, "Griffindor", "yellow");

    @Test
    void create_shouldReturnFacultyAndStatus200() throws Exception {
        when(facultyRepository.save(faculty)).thenReturn(faculty);
        mockMvc.perform(post("/faculty").content(objectMapper.writeValueAsString(faculty))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(faculty));
    }
    @Test
    void read_
}
