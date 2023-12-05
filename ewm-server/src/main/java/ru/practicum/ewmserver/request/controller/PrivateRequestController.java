package ru.practicum.ewmserver.request.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmserver.request.dto.ParticipationRequestDto;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

public interface PrivateRequestController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ParticipationRequestDto postRequest(@PathVariable @PositiveOrZero int userId,
                                        @RequestParam @PositiveOrZero int eventId);

    @GetMapping
    List<ParticipationRequestDto> getRequests(@PathVariable @PositiveOrZero int userId);

    @PatchMapping("/{requestId}/cancel")
    ParticipationRequestDto cancelRequest(@PathVariable @PositiveOrZero int userId,
                                          @PathVariable @PositiveOrZero int requestId);
}
