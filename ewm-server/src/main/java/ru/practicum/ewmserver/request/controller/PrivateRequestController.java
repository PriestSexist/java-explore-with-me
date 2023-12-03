package ru.practicum.ewmserver.request.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmserver.request.dto.ParticipationRequestDto;

import java.util.List;

public interface PrivateRequestController {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ParticipationRequestDto postRequest(@PathVariable int userId, @RequestParam int eventId);

    @GetMapping
    List<ParticipationRequestDto> getRequests(@PathVariable int userId);

    @PatchMapping("/{requestId}/cancel")
    ParticipationRequestDto cancelRequest(@PathVariable int userId,
                                          @PathVariable int requestId);
}
