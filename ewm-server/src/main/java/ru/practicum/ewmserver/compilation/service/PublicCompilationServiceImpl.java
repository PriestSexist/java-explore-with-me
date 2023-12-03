package ru.practicum.ewmserver.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmserver.compilation.dto.CompilationDto;
import ru.practicum.ewmserver.compilation.mapper.CompilationMapper;
import ru.practicum.ewmserver.compilation.model.Compilation;
import ru.practicum.ewmserver.compilation.storage.CompilationRepository;
import ru.practicum.ewmserver.error.exception.EntityNotFoundException;
import ru.practicum.ewmserver.request.storage.RequestRepository;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewmserver.error.constants.ErrorStrings.COMPILATION_NOT_FOUND_BY_ID;

@Service
@RequiredArgsConstructor
public class PublicCompilationServiceImpl implements PublicCompilationService {
    private final CompilationRepository compilationRepository;
    private final RequestRepository requestRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CompilationDto> getCompilation(boolean pinned, int from, int size) {
        PageRequest pageRequest = PageRequest.of(from > 0 ? from / size : 0, size);
        if (pinned) {
            return compilationRepository.getAllByPinned(true, pageRequest).getContent().stream()
                    .map(event -> CompilationMapper.createCompilationDtoWithEventList(event, requestRepository))
                    .collect(Collectors.toList());
        }
        return compilationRepository.findAll(pageRequest).getContent().stream()
                .map(event -> CompilationMapper.createCompilationDtoWithEventList(event, requestRepository))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public CompilationDto getCompilationById(int compilationId) {
        Compilation compilation = compilationRepository.findById(compilationId).orElseThrow(() -> new EntityNotFoundException(String.format(COMPILATION_NOT_FOUND_BY_ID, compilationId)));
        return CompilationMapper.createCompilationDtoWithEventList(compilation, requestRepository);
    }
}
