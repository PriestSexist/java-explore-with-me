package ru.practicum.ewmserver.event.service;

import ru.practicum.ewmserver.event.dto.*;
import ru.practicum.ewmserver.request.dto.ParticipationRequestDto;

import java.util.List;

public interface PrivateEventService {
    EventFullDto postEvent(NewEventDto newEventDto, int userId);

    List<EventShortDto> getUserEvents(int userId, int from, int size);

    EventFullDto getEventById(int userId, int eventId);

    EventFullDto patchEvent(UpdateEventUserRequest updateEventUserRequest, int userId, int eventId);

    List<ParticipationRequestDto> getRequestsInEvent(int userId, int eventId);

    EventRequestStatusUpdateResult patchRequests(EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest, int userId, int eventId);
}
