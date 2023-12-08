package ru.practicum.ewmserver.compilation.service;

import ru.practicum.ewmserver.compilation.dto.CompilationDto;

import java.util.List;

public interface PublicCompilationService {

    List<CompilationDto> getCompilation(boolean pinned, int from, int size);

    CompilationDto getCompilationById(int compId);
}
