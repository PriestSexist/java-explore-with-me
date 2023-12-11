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
public class PrivateEventControllerImpl {

    private final PrivateEventService privateEventService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto postEvent(@RequestBody @Valid NewEventDto newEventDto,
                                  @PathVariable @PositiveOrZero int userId) {
        log.debug("Вызван метод postEvent");
        return privateEventService.postEvent(newEventDto, userId);
    }

    @GetMapping
    public List<EventShortDto> getUserEvents(@PathVariable @PositiveOrZero int userId,
                                             @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                             @RequestParam(defaultValue = "10") @Positive int size) {
        log.debug("Вызван метод getUserEvents");
        return privateEventService.getUserEvents(userId, from, size);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventById(@PathVariable @PositiveOrZero int userId,
                                     @PathVariable @PositiveOrZero int eventId) {
        log.debug("Вызван метод getEventById");
        return privateEventService.getEventById(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto patchEvent(@RequestBody @Valid UpdateEventUserRequest updateEventUserRequest,
                                   @PathVariable @PositiveOrZero int userId,
                                   @PathVariable @PositiveOrZero int eventId) {
        log.debug("Вызван метод patchEvent");
        return privateEventService.patchEvent(updateEventUserRequest, userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getRequestsInEvent(@PathVariable @PositiveOrZero int userId,
                                                            @PathVariable @PositiveOrZero int eventId) {
        log.debug("Вызван метод getRequestsInEvent");
        return privateEventService.getRequestsInEvent(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult patchRequests(@RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest,
                                                        @PathVariable @PositiveOrZero int userId,
                                                        @PathVariable @PositiveOrZero int eventId) {
        log.debug("Вызван метод patchRequests");
        return privateEventService.patchRequests(eventRequestStatusUpdateRequest, userId, eventId);
    }

    @PostMapping("/{eventId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto postComment(@RequestBody @Valid CommentRequest commentRequest,
                                  @PathVariable @PositiveOrZero int userId,
                                  @PathVariable @PositiveOrZero int eventId) {
        log.debug("Вызван метод postComment");
        return privateEventService.postComment(commentRequest, userId, eventId);
    }

    @PatchMapping("/{eventId}/comments/{commentId}")
    public CommentDto patchComment(@RequestBody @Valid CommentRequest commentRequest,
                                   @PathVariable @PositiveOrZero int userId,
                                   @PathVariable @PositiveOrZero int eventId,
                                   @PathVariable @PositiveOrZero int commentId) {
        log.debug("Вызван метод patchRequests");
        return privateEventService.patchComment(commentRequest, userId, eventId, commentId);
    }

    @DeleteMapping("/{eventId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable @PositiveOrZero int userId,
                              @PathVariable @PositiveOrZero int eventId,
                              @PathVariable @PositiveOrZero int commentId) {
        log.debug("Вызван метод deleteComment");
        privateEventService.deleteComment(userId, eventId, commentId);
    }

    @GetMapping("/{eventId}/comments")
    public List<CommentDto> getComments(@PathVariable @PositiveOrZero int userId,
                                        @PathVariable @PositiveOrZero int eventId,
                                        @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                        @RequestParam(defaultValue = "10") @Positive int size) {
        log.debug("Вызван метод getComments");
        return privateEventService.getComments(userId, eventId, from, size);
    }

}
