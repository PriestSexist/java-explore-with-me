package ru.practicum.ewmserver.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewmserver.category.dto.CategoryDto;
import ru.practicum.ewmserver.event.model.Location;
import ru.practicum.ewmserver.event.model.EventState;
import ru.practicum.ewmserver.user.dto.UserShortDto;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class EventFullDto {
    private final int id;
    private final String annotation;
    private final String description;
    private final CategoryDto category;
    private final int confirmedRequests;
    private final String createdOn;
    private final String eventDate;
    private final UserShortDto initiator;
    private final Location location;
    private final Boolean paid;
    private final int participantLimit;
    private final String publishedOn;
    private final Boolean requestModeration;
    private final EventState state;
    private final String title;
    private final int views;
}
