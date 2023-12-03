package ru.practicum.ewmserver.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewmserver.category.dto.CategoryDto;
import ru.practicum.ewmserver.user.dto.UserShortDto;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class EventShortDto {
    private final int id;
    private final String annotation;
    private final CategoryDto category;
    private final int confirmedRequests;
    private final String eventDate;
    private final UserShortDto initiator;
    private final boolean paid;
    private final String title;
    private final int views;
}
