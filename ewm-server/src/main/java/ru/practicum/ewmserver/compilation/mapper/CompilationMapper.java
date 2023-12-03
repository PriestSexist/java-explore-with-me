package ru.practicum.ewmserver.compilation.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewmserver.compilation.dto.CompilationDto;
import ru.practicum.ewmserver.compilation.dto.NewCompilationDto;
import ru.practicum.ewmserver.compilation.model.Compilation;
import ru.practicum.ewmserver.event.dto.EventShortDto;
import ru.practicum.ewmserver.event.mapper.EventMapper;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class CompilationMapper {
    public static Compilation createCompilation(NewCompilationDto newCompilationDto) {
        return Compilation.builder()
                .pinned(newCompilationDto.getPinned())
                .title(newCompilationDto.getTitle())
                .build();
    }

    public static CompilationDto createCompilationDtoWithoutEventList(Compilation compilation, List<EventShortDto> eventsList) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .pinned(compilation.getPinned())
                .title(compilation.getTitle())
                .events(eventsList)
                .build();
    }
    public static CompilationDto createCompilationDtoWithEventList(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .pinned(compilation.getPinned())
                .title(compilation.getTitle())
                .events(compilation.getEvents().stream()
                        .map(EventMapper::createEventShortDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
