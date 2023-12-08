package ru.practicum.ewmserver.event.service;

import ru.practicum.ewmserver.event.dto.EventFullDto;
import ru.practicum.ewmserver.event.dto.UpdateEventAdminRequest;

import java.time.LocalDateTime;
import java.util.List;


public interface AdminEventService {
    EventFullDto patchEvent(UpdateEventAdminRequest updateEventAdminRequest, int eventId);

    List<EventFullDto> getEvents(List<Integer> users, List<String> states, List<Integer> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size);
}
