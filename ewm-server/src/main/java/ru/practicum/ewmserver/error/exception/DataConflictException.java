package ru.practicum.ewmserver.error.exception;

public class DataConflictException extends RuntimeException {
    public DataConflictException(String message) {
        super(message);
    }
}
