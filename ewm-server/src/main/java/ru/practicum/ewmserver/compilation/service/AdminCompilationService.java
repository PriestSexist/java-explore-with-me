package ru.practicum.ewmserver.compilation.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmserver.compilation.dto.CompilationDto;
import ru.practicum.ewmserver.compilation.dto.NewCompilationDto;
import ru.practicum.ewmserver.compilation.dto.UpdateCompilationRequest;

public interface AdminCompilationService {
    @Transactional(propagation = Propagation.REQUIRED)
    CompilationDto postCompilation(NewCompilationDto newCompilationDto);

    @Transactional(propagation = Propagation.REQUIRED)
    void deleteCompilation(int compilationId);

    @Transactional(propagation = Propagation.REQUIRED)
    CompilationDto patchCompilation(UpdateCompilationRequest updateCompilationRequest, int compilationId);
}
