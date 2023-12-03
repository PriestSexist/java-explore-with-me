package ru.practicum.ewmserver.event.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmserver.event.dto.EventFullDto;
import ru.practicum.ewmserver.event.dto.UpdateEventAdminRequest;

import java.time.LocalDateTime;
import java.util.List;


public interface AdminEventService {

    @Transactional(propagation = Propagation.REQUIRED)
    EventFullDto patchEvent(UpdateEventAdminRequest updateEventAdminRequest, int eventId);

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    List<EventFullDto> getEvents(List<Integer> users, List<String> states, List<Integer> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size);
}
