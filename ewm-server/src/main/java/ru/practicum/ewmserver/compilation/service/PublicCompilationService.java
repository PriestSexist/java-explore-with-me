package ru.practicum.ewmserver.compilation.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmserver.compilation.dto.CompilationDto;

import java.util.List;

public interface PublicCompilationService {

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    List<CompilationDto> getCompilation(boolean pinned, int from, int size);

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    CompilationDto getCompilationById(int compId);
}
