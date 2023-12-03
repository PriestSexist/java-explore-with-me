package ru.practicum.ewmserver.event.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmserver.category.storage.CategoryRepository;
import ru.practicum.ewmserver.error.exception.DataConflictException;
import ru.practicum.ewmserver.error.exception.EntityNotFoundException;
import ru.practicum.ewmserver.error.exception.InvalidRequestException;
import ru.practicum.ewmserver.event.dto.EventFullDto;
import ru.practicum.ewmserver.event.dto.UpdateEventAdminRequest;
import ru.practicum.ewmserver.event.mapper.EventMapper;
import ru.practicum.ewmserver.category.model.Category;
import ru.practicum.ewmserver.event.model.Event;
import ru.practicum.ewmserver.event.model.EventState;
import ru.practicum.ewmserver.event.model.ModeratorEventState;
import ru.practicum.ewmserver.event.storage.EventRepository;
import ru.practicum.statdto.dto.Constants;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewmserver.error.constants.ErrorStrings.*;

@Service
@RequiredArgsConstructor
public class AdminEventServiceImpl implements AdminEventService {

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public EventFullDto patchEvent(UpdateEventAdminRequest updateEventAdminRequest, int eventId) {

        Event eventFromDb = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException(String.format(EVENT_NOT_FOUND_BY_ID, eventId)));

        if (updateEventAdminRequest.getStateAction() != null) {
            ModeratorEventState action = updateEventAdminRequest.getStateAction();
            switch (action) {
                case PUBLISH_EVENT:

                    if (eventFromDb.getState() != EventState.PENDING) {
                        throw new DataConflictException(CANNOT_PUBLISH_EVENT_NOT_PENDING);
                    }

                    if (LocalDateTime.now().plusHours(1).isAfter(eventFromDb.getEventDate())) {
                        throw new DataConflictException(CANNOT_PUBLISH_EVENT_LESS_THEN_ONE_HOUR_ON_SITE);
                    }

                    eventFromDb.setState(EventState.PUBLISHED);
                    eventFromDb.setPublishedOn(LocalDateTime.now());

                    break;

                case REJECT_EVENT:

                    if (eventFromDb.getState() != EventState.PENDING) {
                        throw new DataConflictException(CANNOT_REJECT_EVENT_NOT_PENDING);
                    }

                    eventFromDb.setState(EventState.CANCELED);

                    break;

                default:
                    throw new IllegalArgumentException(INVALID_ACTION + action);
            }

            if (updateEventAdminRequest.getEventDate() != null) {
                if (LocalDateTime.parse(updateEventAdminRequest.getEventDate(), Constants.FORMATTER).isBefore(LocalDateTime.now().plusHours(1))) {
                    throw new InvalidRequestException(CANNOT_PUBLISH_EVENT_LESS_THEN_ONE_HOUR_ON_SITE);
                }
                eventFromDb.setEventDate(LocalDateTime.parse(updateEventAdminRequest.getEventDate(), Constants.FORMATTER));
            }

            if (updateEventAdminRequest.getAnnotation() != null) {
                eventFromDb.setAnnotation(eventFromDb.getAnnotation());
            }

            if (updateEventAdminRequest.getCategory() != eventFromDb.getCategory().getId()) {
                Category category = categoryRepository.findById(updateEventAdminRequest.getCategory()).orElseThrow(() -> new EntityNotFoundException(String.format(CATEGORY_NOT_FOUND_BY_ID, updateEventAdminRequest.getCategory())));
                eventFromDb.setCategory(category);
            }

            if (updateEventAdminRequest.getDescription() != null) {
                eventFromDb.setDescription(updateEventAdminRequest.getDescription());
            }

            if (updateEventAdminRequest.getLocation() != null) {
                eventFromDb.setLocation(updateEventAdminRequest.getLocation());
            }

            if (updateEventAdminRequest.getPaid() != null) {
                eventFromDb.setPaid(updateEventAdminRequest.getPaid());
            }

            if (updateEventAdminRequest.getParticipantLimit() != null) {
                eventFromDb.setParticipantLimit(updateEventAdminRequest.getParticipantLimit());
            }

            if (updateEventAdminRequest.getRequestModeration() != null) {
                eventFromDb.setRequestModeration(updateEventAdminRequest.getRequestModeration());
            }

            if (updateEventAdminRequest.getTitle() != null) {
                eventFromDb.setTitle(updateEventAdminRequest.getTitle());
            }
        }
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<EventFullDto> getEvents(List<Integer> usersIds, List<String> states, List<Integer> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size) {

        if (rangeStart == null) {
            rangeStart = LocalDateTime.now();
            rangeEnd = rangeStart.plusYears(1000);
        }

        if (rangeEnd.isBefore(rangeStart)) {
            throw new InvalidRequestException(INVAlID_TIME_PARAMETERS);
        }

        if (states != null) {
            for (String state : states) {
                if (!EnumUtils.isValidEnum(EventState.class, state)) {
                    throw new InvalidRequestException(INVALID_STATE + state);
                }
            }
        }
        for (String state : states) {
            if (!EnumUtils.isValidEnum(EventState.class, state)) {
                throw new InvalidRequestException(INVALID_STATE + state);
            }
        }

        PageRequest pageRequest = PageRequest.of(from > 0 ? from / size : 0, size);

        Page<Event> events = eventRepository.getByUserIdsStatesCategories(usersIds, states, categories, rangeStart, rangeEnd, pageRequest);

        return events.getContent().stream()
                .map(EventMapper::createEventFullDto)
                .collect(Collectors.toList());
    }
}