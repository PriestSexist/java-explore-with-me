package ru.practicum.ewmserver.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmserver.compilation.dto.CompilationDto;
import ru.practicum.ewmserver.compilation.dto.NewCompilationDto;
import ru.practicum.ewmserver.compilation.dto.UpdateCompilationRequest;
import ru.practicum.ewmserver.compilation.mapper.CompilationMapper;
import ru.practicum.ewmserver.compilation.model.Compilation;
import ru.practicum.ewmserver.compilation.model.EventCompilationConnection;
import ru.practicum.ewmserver.compilation.storage.CompilationRepository;
import ru.practicum.ewmserver.compilation.storage.EventCompilationConnectionRepository;
import ru.practicum.ewmserver.error.exception.DataConflictException;
import ru.practicum.ewmserver.error.exception.EntityNotFoundException;
import ru.practicum.ewmserver.event.dto.EventShortDto;
import ru.practicum.ewmserver.event.mapper.EventMapper;
import ru.practicum.ewmserver.event.model.Event;
import ru.practicum.ewmserver.event.storage.EventRepository;
import ru.practicum.ewmserver.request.model.RequestStatus;
import ru.practicum.ewmserver.request.storage.RequestRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewmserver.error.constants.ErrorStrings.COMPILATION_NOT_FOUND_BY_ID;
import static ru.practicum.ewmserver.error.constants.ErrorStrings.COMPILATION_NOT_FOUND_BY_TITLE;

@Service
@RequiredArgsConstructor
public class AdminCompilationServiceImpl implements AdminCompilationService {

    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;
    private final CompilationRepository compilationRepository;
    private final EventCompilationConnectionRepository eventCompilationConnectionRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CompilationDto postCompilation(NewCompilationDto compilationDto) {

        if (compilationRepository.existsByTitle(compilationDto.getTitle())) {
            throw new DataConflictException(String.format(COMPILATION_NOT_FOUND_BY_TITLE, compilationDto.getTitle()));
        }

        List<EventShortDto> eventShortDtoList = new ArrayList<>();

        Compilation compilation = CompilationMapper.createCompilation(compilationDto);
        Compilation compilationFromDb = compilationRepository.save(compilation);

        if (compilationDto.getEvents() != null && !compilationDto.getEvents().isEmpty()) {
            List<Event> events = eventRepository.findAllById(new ArrayList<>(compilationDto.getEvents()));
            eventShortDtoList = events.stream().map(event -> EventMapper.createEventShortDto(event, requestRepository.countRequestByEventIdAndStatus(event.getId(), RequestStatus.CONFIRMED))).collect(Collectors.toList());
            for (Integer eventId : compilationDto.getEvents()) {
                eventCompilationConnectionRepository.save(new EventCompilationConnection(0, eventId, compilationFromDb.getId()));
            }
            return CompilationMapper.createCompilationDtoWithoutEventList(compilation, eventShortDtoList);
        }

        return CompilationMapper.createCompilationDtoWithoutEventList(compilationFromDb, eventShortDtoList);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteCompilation(int compilationId) {
        if (!compilationRepository.existsById(compilationId)) {
            throw new EntityNotFoundException(String.format(COMPILATION_NOT_FOUND_BY_ID, compilationId));
        }
        compilationRepository.deleteById(compilationId);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CompilationDto patchCompilation(UpdateCompilationRequest updateCompilationRequest, int compilationId) {

        Compilation compilationFromDb = compilationRepository.findById(compilationId).orElseThrow(() -> new EntityNotFoundException(String.format(COMPILATION_NOT_FOUND_BY_ID, compilationId)));

        if (updateCompilationRequest.getTitle() != null && !updateCompilationRequest.getTitle().isBlank()) {
            compilationFromDb.setTitle(updateCompilationRequest.getTitle());
        }

        if (updateCompilationRequest.getPinned() != null) {
            compilationFromDb.setPinned(updateCompilationRequest.getPinned());
        }

        if (updateCompilationRequest.getEvents() != null) {
            if (updateCompilationRequest.getEvents().isEmpty()) {
                compilationFromDb.setEvents(new ArrayList<>());
            } else {
                compilationFromDb.setEvents(eventRepository.findAllById(new ArrayList<>(updateCompilationRequest.getEvents())));
            }
        }

        return CompilationMapper.createCompilationDtoWithEventList(compilationRepository.save(compilationFromDb), requestRepository);
    }
}
