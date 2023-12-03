package ru.practicum.ewmserver.event.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmserver.event.dto.*;
import ru.practicum.ewmserver.request.dto.ParticipationRequestDto;

import java.util.List;

public interface PrivateEventService {
    @Transactional(propagation = Propagation.REQUIRED)
    EventFullDto postEvent(NewEventDto newEventDto, int userId);

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    List<EventShortDto> getUserEvents(int userId, int from, int size);

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    EventFullDto getEventById(int userId, int eventId);

    @Transactional(propagation = Propagation.REQUIRED)
    EventFullDto patchEvent(UpdateEventUserRequest updateEventUserRequest, int userId, int eventId);

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    List<ParticipationRequestDto> getRequestsInEvent(int userId, int eventId);

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    EventRequestStatusUpdateResult patchRequests(EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest, int userId, int eventId);
}
