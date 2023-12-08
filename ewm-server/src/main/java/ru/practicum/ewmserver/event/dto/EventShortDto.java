package ru.practicum.ewmserver.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewmserver.category.dto.CategoryDto;
import ru.practicum.ewmserver.user.dto.UserShortDto;
import ru.practicum.statdto.dto.Constants;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class EventShortDto {
    private final int id;
    private final String annotation;
    private final CategoryDto category;
    private final int confirmedRequests;
    @JsonFormat(pattern = Constants.DATETIME_FORMAT)
    private LocalDateTime eventDate;
    private final UserShortDto initiator;
    private final boolean paid;
    private final String title;
    private final int views;
}
