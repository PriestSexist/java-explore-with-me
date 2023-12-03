package ru.practicum.ewmserver.event.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmserver.compilation.dto.CompilationDto;
import ru.practicum.ewmserver.event.dto.EventFullDto;
import ru.practicum.ewmserver.event.dto.UpdateEventAdminRequest;
import ru.practicum.statdto.dto.Constants;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

public interface AdminEventController {

    @PatchMapping("/{eventId}")
    EventFullDto patchEvent(@RequestBody UpdateEventAdminRequest updateEventAdminRequest,
                            @PathVariable @Positive int eventId);

    @GetMapping
    List<EventFullDto> getEvents(@RequestParam List<Integer> users,
                                 @RequestParam List<String> states,
                                 @RequestParam List<Integer> categories,
                                 @RequestParam @DateTimeFormat(pattern = Constants.DATETIME_FORMAT) LocalDateTime rangeStart,
                                 @RequestParam @DateTimeFormat(pattern = Constants.DATETIME_FORMAT) LocalDateTime rangeEnd,
                                 @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                 @RequestParam(defaultValue = "10") @Positive int size);
}
