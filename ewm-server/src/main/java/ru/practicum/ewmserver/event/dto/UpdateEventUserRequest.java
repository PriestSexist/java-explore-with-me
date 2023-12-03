package ru.practicum.ewmserver.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewmserver.error.constants.ErrorStrings;
import ru.practicum.ewmserver.event.model.Location;
import ru.practicum.ewmserver.event.model.StateAction;
import ru.practicum.statdto.dto.Constants;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UpdateEventUserRequest {
    @Size(min = 20, message = ErrorStrings.TOO_SHORT)
    @Size(max = 2000, message = ErrorStrings.TOO_LONG)
    private final String annotation;
    @Positive
    private final int category;
    @Size(min = 20, message = ErrorStrings.TOO_SHORT)
    @Size(max = 7000, message = ErrorStrings.TOO_LONG)
    private final String description;
    @JsonFormat(pattern = Constants.DATETIME_FORMAT)
    private final LocalDateTime eventDate;
    private final Location location;
    private final Boolean paid;
    @Positive
    private final int participantLimit;
    private final Boolean requestModeration;
    private final StateAction stateAction;
    @Size(min = 3, message = ErrorStrings.TOO_SHORT)
    @Size(max = 120, message = ErrorStrings.TOO_LONG)
    private final String title;
}
