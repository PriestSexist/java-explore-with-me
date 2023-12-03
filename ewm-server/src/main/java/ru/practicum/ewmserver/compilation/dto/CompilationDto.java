package ru.practicum.ewmserver.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewmserver.event.dto.EventShortDto;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CompilationDto {
    private final int id;
    private final Boolean pinned;
    private final String title;
    private final List<EventShortDto> events;
}
