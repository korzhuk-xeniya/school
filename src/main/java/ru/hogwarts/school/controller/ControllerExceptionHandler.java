package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.hogwarts.school.exception.FacultyAlreadyExsitsException;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.exception.StudendAlreadyExsitsException;
import ru.hogwarts.school.exception.StudentNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler({StudendAlreadyExsitsException.class, FacultyAlreadyExsitsException.class})
    public ResponseEntity<String> handelException(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler({StudentNotFoundException.class, FacultyNotFoundException.class})
    public ResponseEntity<String> handelNotFoundException(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }
}

