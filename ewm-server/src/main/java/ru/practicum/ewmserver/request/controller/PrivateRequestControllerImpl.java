package ru.practicum.ewmserver.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmserver.request.dto.ParticipationRequestDto;
import ru.practicum.ewmserver.request.service.PrivateRequestService;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
@Validated
public class PrivateRequestControllerImpl implements PrivateRequestController {

    private final PrivateRequestService privateRequestService;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto postRequest(@PathVariable @PositiveOrZero int userId,
                                               @RequestParam @PositiveOrZero int eventId) {
        log.debug("Вызван метод postRequest");
        return privateRequestService.postRequest(userId, eventId);
    }

    @Override
    @GetMapping
    public List<ParticipationRequestDto> getRequests(@PathVariable @PositiveOrZero int userId) {
        log.debug("Вызван метод getRequests");
        return privateRequestService.getRequests(userId);
    }

    @Override
    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable @PositiveOrZero int userId,
                                                 @PathVariable @PositiveOrZero int requestId) {
        log.debug("Вызван метод cancelRequest");
        return privateRequestService.cancelRequest(userId, requestId);
    }
}
