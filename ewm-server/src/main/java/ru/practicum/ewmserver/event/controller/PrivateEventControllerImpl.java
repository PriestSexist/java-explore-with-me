package ru.practicum.ewmserver.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmserver.event.dto.*;
import ru.practicum.ewmserver.event.service.PrivateEventService;
import ru.practicum.ewmserver.request.dto.ParticipationRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events")
@Slf4j
@Validated
public class PrivateEventControllerImpl implements PrivateEventController {

    private final PrivateEventService privateEventService;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto postEvent(@RequestBody @Valid NewEventDto newEventDto,
                                  @PathVariable @Positive int userId) {
        log.debug("Вызван метод postEvent");
        return privateEventService.postEvent(newEventDto, userId);
    }

    @Override
    @GetMapping
    public List<EventShortDto> getUserEvents(@PathVariable @Positive int userId,
                                             @RequestParam @PositiveOrZero int from,
                                             @RequestParam @Positive int size) {
        log.debug("Вызван метод getUserEvents");
        return privateEventService.getUserEvents(userId, from, size);
    }

    @Override
    @GetMapping("/{eventId}")
    public EventFullDto getEventById(@PathVariable @Positive int userId,
                                     @PathVariable @Positive int eventId) {
        log.debug("Вызван метод getEventById");
        return privateEventService.getEventById(userId, eventId);
    }

    @Override
    @PatchMapping("/{eventId}")
    public EventFullDto patchEvent(@RequestBody @Valid UpdateEventUserRequest updateEventUserRequest,
                                   @PathVariable @Positive int userId,
                                   @PathVariable @Positive int eventId) {
        log.debug("Вызван метод patchEvent");
        return privateEventService.patchEvent(updateEventUserRequest, userId, eventId);
    }

    @Override
    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getRequestsInEvent(@PathVariable @Positive int userId,
                                                            @PathVariable @Positive int eventId) {
        log.debug("Вызван метод getRequestsInEvent");
        return privateEventService.getRequestsInEvent(userId, eventId);
    }

    @Override
    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult patchRequests(@RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest,
                                                        @PathVariable @Positive int userId,
                                                        @PathVariable @Positive int eventId) {
        log.debug("Вызван метод patchRequests");
        return privateEventService.patchRequests(eventRequestStatusUpdateRequest, userId, eventId);
    }

}
