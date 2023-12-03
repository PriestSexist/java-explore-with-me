package ru.practicum.ewmserver.error.model;

import lombok.Data;
import org.springframework.http.HttpStatus;
import ru.practicum.statdto.dto.Constants;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ApiError {
    private final List<String> errors;
    private final String message;
    private final String reason;
    private final String status;
    private final String timestamp;

    public ApiError(List<String> errors, String message, String reason, HttpStatus status) {
        this.errors = errors;
        this.message = message;
        this.reason = reason;
        this.status = status.name();
        this.timestamp = Constants.FORMATTER.format(LocalDateTime.now());
    }
}
