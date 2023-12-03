package ru.practicum.ewmserver.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmserver.error.exception.EntityNotFoundException;
import ru.practicum.ewmserver.error.exception.InvalidRequestException;
import ru.practicum.ewmserver.event.dto.EventFullDto;
import ru.practicum.ewmserver.event.dto.EventShortDto;
import ru.practicum.ewmserver.event.mapper.EventMapper;
import ru.practicum.ewmserver.event.model.Event;
import ru.practicum.ewmserver.event.model.EventState;
import ru.practicum.ewmserver.event.storage.EventRepository;
import ru.practicum.ewmserver.request.model.RequestStatus;
import ru.practicum.ewmserver.request.storage.RequestRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewmserver.error.constants.ErrorStrings.INVALID_SORTING_PARAMETERS;
import static ru.practicum.ewmserver.error.constants.ErrorStrings.INVAlID_TIME_PARAMETERS;

@Service
@RequiredArgsConstructor
public class PublicEventServiceImpl implements PublicEventService {

    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<EventShortDto> getEvents(String text, List<Integer> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, int from, int size) {

        if (text.isBlank()) {
            text = "";
        }

        if (!sort.equalsIgnoreCase("EVENT_DATE") && !sort.equalsIgnoreCase("VIEWS")) {
            throw new InvalidRequestException(INVALID_SORTING_PARAMETERS);
        }

        if (rangeStart == null && rangeEnd == null) {
            rangeStart = LocalDateTime.now();
            rangeEnd = rangeStart.plusYears(1000);
        }

        if (rangeStart == null || rangeEnd == null || rangeStart.isAfter(rangeEnd)) {
            throw new InvalidRequestException(INVAlID_TIME_PARAMETERS);
        }

        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);

        Page<Event> events;

        if (!paid) {
            if (onlyAvailable) {
                events = eventRepository.getBySearchAvailable(EventState.PUBLISHED, text, categories, rangeStart, rangeEnd, sort, page);
            } else {
                events = eventRepository.getBySearch(EventState.PUBLISHED, text, categories, rangeStart, rangeEnd, sort, page);
            }
        } else {
            if (onlyAvailable) {
                events = eventRepository.getBySearchAndPaidAvailable(EventState.PUBLISHED, text, categories, rangeStart, rangeEnd, true, sort, page);
            } else {
                events = eventRepository.getBySearchAndPaid(EventState.PUBLISHED, text, categories, rangeStart, rangeEnd, true, sort, page);
            }
        }

        return events.getContent().stream()
                .map(event -> EventMapper.createEventShortDto(event, requestRepository.countRequestByEventIdAndStatus(event.getId(), RequestStatus.CONFIRMED)))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public EventFullDto getEventById(int id) {

        Event eventFromDb = eventRepository.getByIdAndState(id, EventState.PUBLISHED).orElseThrow(() -> new EntityNotFoundException(String.format("Event with id=%d was not found", id)));

        eventFromDb.setViews(eventFromDb.getViews() + 1);
        eventRepository.save(eventFromDb);
        return EventMapper.createEventFullDto(eventRepository.save(eventFromDb), requestRepository.countRequestByEventIdAndStatus(eventFromDb.getId(), RequestStatus.CONFIRMED));
    }
}
