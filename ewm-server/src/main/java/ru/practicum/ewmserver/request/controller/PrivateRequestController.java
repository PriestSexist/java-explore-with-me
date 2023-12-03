package ru.practicum.ewmserver.request.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmserver.request.dto.ParticipationRequestDto;

import javax.validation.constraints.Positive;
import java.util.List;

public interface PrivateRequestController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ParticipationRequestDto postRequest(@PathVariable @Positive int userId,
                                        @RequestParam @Positive int eventId);

    @GetMapping
    List<ParticipationRequestDto> getRequests(@PathVariable @Positive int userId);

    @PatchMapping("/{requestId}/cancel")
    ParticipationRequestDto cancelRequest(@PathVariable @Positive int userId,
                                          @PathVariable @Positive int requestId);
}
