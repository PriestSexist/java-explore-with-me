package ru.practicum.ewmserver.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmserver.compilation.dto.CompilationDto;
import ru.practicum.ewmserver.compilation.storage.CompilationRepository;

import java.util.List;

public interface PublicCompilationService {

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    List<CompilationDto> getCompilation(boolean pinned, int from, int size);

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    CompilationDto getCompilationById(int compId);
}
