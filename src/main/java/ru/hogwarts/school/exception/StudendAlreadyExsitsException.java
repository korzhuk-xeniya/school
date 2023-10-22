package ru.hogwarts.school.exception;

public class StudendAlreadyExsitsException extends RuntimeException {
    public StudendAlreadyExsitsException(String message) {
        super(message);
    }
}
