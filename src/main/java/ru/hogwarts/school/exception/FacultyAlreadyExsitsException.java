package ru.hogwarts.school.exception;

public class FacultyAlreadyExsitsException extends RuntimeException {
    public FacultyAlreadyExsitsException(String message) {
        super(message);
    }
}
