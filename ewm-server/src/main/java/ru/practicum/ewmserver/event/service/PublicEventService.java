package ru.practicum.ewmserver.event.service;

import ru.practicum.ewmserver.event.dto.EventFullDto;
import ru.practicum.ewmserver.event.dto.EventShortDto;

import java.time.LocalDateTime;
import java.util.List;

public interface PublicEventService {
    List<EventShortDto> getEvents(String text, List<Integer> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, int from, int size);

    EventFullDto getEventById(int id);
}
