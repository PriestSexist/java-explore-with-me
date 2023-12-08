package ru.practicum.ewmserver.compilation.service;

import ru.practicum.ewmserver.compilation.dto.CompilationDto;
import ru.practicum.ewmserver.compilation.dto.NewCompilationDto;
import ru.practicum.ewmserver.compilation.dto.UpdateCompilationRequest;

public interface AdminCompilationService {
    CompilationDto postCompilation(NewCompilationDto newCompilationDto);

    void deleteCompilation(int compilationId);

    CompilationDto patchCompilation(UpdateCompilationRequest updateCompilationRequest, int compilationId);
}
