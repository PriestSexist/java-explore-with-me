package ru.practicum.ewmserver.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewmserver.category.dto.CategoryDto;
import ru.practicum.ewmserver.event.model.EventState;
import ru.practicum.ewmserver.event.model.Location;
import ru.practicum.ewmserver.user.dto.UserShortDto;
import ru.practicum.statdto.dto.Constants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class EventFullDto {
    private final int id;
    @NotBlank
    private final String annotation;
    @NotBlank
    private final String description;
    @NotNull
    private final CategoryDto category;
    private final int confirmedRequests;
    @JsonFormat(pattern = Constants.DATETIME_FORMAT)
    private final LocalDateTime createdOn;
    @NotNull
    @JsonFormat(pattern = Constants.DATETIME_FORMAT)
    private final LocalDateTime eventDate;
    @NotNull
    private final UserShortDto initiator;
    @NotNull
    private final Location location;
    @NotNull
    private final Boolean paid;
    private final int participantLimit;
    @JsonFormat(pattern = Constants.DATETIME_FORMAT)
    private LocalDateTime publishedOn;
    private final Boolean requestModeration;
    private final EventState state;
    @NotBlank
    private final String title;
    private final int views;
}
