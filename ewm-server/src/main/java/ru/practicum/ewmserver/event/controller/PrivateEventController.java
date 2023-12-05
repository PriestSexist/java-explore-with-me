package ru.practicum.ewmserver.event.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmserver.event.dto.*;
import ru.practicum.ewmserver.request.dto.ParticipationRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

public interface PrivateEventController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    EventFullDto postEvent(@RequestBody @Valid NewEventDto newEventDto,
                           @PathVariable @PositiveOrZero int userId);

    @GetMapping
    List<EventShortDto> getUserEvents(@PathVariable @PositiveOrZero int userId,
                                      @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                      @RequestParam(defaultValue = "10") @Positive int size);

    @GetMapping("/{eventId}")
    EventFullDto getEventById(@PathVariable @PositiveOrZero int userId,
                              @PathVariable @PositiveOrZero int eventId);

    @PatchMapping("/{eventId}")
    EventFullDto patchEvent(@RequestBody @Valid UpdateEventUserRequest updateEventUserRequest,
                            @PathVariable @PositiveOrZero int userId,
                            @PathVariable @PositiveOrZero int eventId);

    @GetMapping("/{eventId}/requests")
    List<ParticipationRequestDto> getRequestsInEvent(@PathVariable @PositiveOrZero int userId,
                                                     @PathVariable @PositiveOrZero int eventId);

    @PatchMapping("/{eventId}/requests")
    EventRequestStatusUpdateResult patchRequests(@RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest,
                                                 @PathVariable @PositiveOrZero int userId,
                                                 @PathVariable @PositiveOrZero int eventId);
}
