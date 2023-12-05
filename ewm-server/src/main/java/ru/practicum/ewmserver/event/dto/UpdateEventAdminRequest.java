package ru.practicum.ewmserver.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewmserver.error.constants.ErrorStrings;
import ru.practicum.ewmserver.event.model.Location;
import ru.practicum.statdto.dto.Constants;

import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UpdateEventAdminRequest {
    @Size(min = 20, message = ErrorStrings.TOO_SHORT)
    @Size(max = 2000, message = ErrorStrings.TOO_LONG)
    private final String annotation;
    private final Integer category;
    @Size(min = 20, message = ErrorStrings.TOO_SHORT)
    @Size(max = 7000, message = ErrorStrings.TOO_LONG)
    private final String description;
    @JsonFormat(pattern = Constants.DATETIME_FORMAT)
    private final String eventDate;
    private final Location location;
    private final Boolean paid;
    @PositiveOrZero
    private final Integer participantLimit;
    private final Boolean requestModeration;
    private final String stateAction;
    @Size(min = 3, message = ErrorStrings.TOO_SHORT)
    @Size(max = 120, message = ErrorStrings.TOO_LONG)
    private final String title;
}
