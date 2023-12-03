package ru.practicum.ewmserver.event.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.ewmserver.event.dto.EventFullDto;
import ru.practicum.ewmserver.event.dto.EventShortDto;
import ru.practicum.statdto.dto.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

public interface PublicEventController {
    @GetMapping
    List<EventShortDto> getEvents(@RequestParam(defaultValue = "") String text,
                                  @RequestParam(defaultValue = "") List<Integer> categories,
                                  @RequestParam(required = false) Boolean paid,
                                  @RequestParam(required = false) @DateTimeFormat(pattern = Constants.DATETIME_FORMAT) LocalDateTime rangeStart,
                                  @RequestParam(required = false) @DateTimeFormat(pattern = Constants.DATETIME_FORMAT) LocalDateTime rangeEnd,
                                  @RequestParam(defaultValue = "") Boolean onlyAvailable,
                                  @RequestParam(defaultValue = "EVENT_DATE") String sort,
                                  @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                  @RequestParam(defaultValue = "10") @Positive int size,
                                  HttpServletRequest request);

    @GetMapping("/{id}")
    EventFullDto getEventById(@PathVariable int id,
                              HttpServletRequest request);
}
