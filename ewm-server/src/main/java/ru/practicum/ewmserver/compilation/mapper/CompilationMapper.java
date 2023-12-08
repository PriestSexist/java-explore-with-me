package ru.practicum.ewmserver.compilation.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewmserver.compilation.dto.CompilationDto;
import ru.practicum.ewmserver.compilation.dto.NewCompilationDto;
import ru.practicum.ewmserver.compilation.model.Compilation;
import ru.practicum.ewmserver.event.dto.EventShortDto;
import ru.practicum.ewmserver.event.mapper.EventMapper;
import ru.practicum.ewmserver.request.model.RequestStatus;
import ru.practicum.ewmserver.request.storage.RequestRepository;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class CompilationMapper {

    public static Compilation createCompilation(NewCompilationDto newCompilationDto) {
        return Compilation.builder()
                .title(newCompilationDto.getTitle())
                .pinned(newCompilationDto.getPinned() != null)
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

    public static CompilationDto createCompilationDtoWithEventList(Compilation compilation, RequestRepository requestRepository) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .pinned(compilation.getPinned())
                .title(compilation.getTitle())
                .events(compilation.getEvents().stream()
                        .map(event -> EventMapper.createEventShortDto(event, requestRepository.countRequestByEventIdAndStatus(event.getId(), RequestStatus.CONFIRMED)))
                        .collect(Collectors.toList()))
                .build();
    }
}
