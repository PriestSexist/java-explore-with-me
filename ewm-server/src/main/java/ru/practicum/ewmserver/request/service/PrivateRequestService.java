package ru.practicum.ewmserver.request.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmserver.request.dto.ParticipationRequestDto;

import java.util.List;

public interface PrivateRequestService {
    @Transactional(propagation = Propagation.REQUIRED)
    ParticipationRequestDto postRequest(int userId, int eventId);
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    List<ParticipationRequestDto> getRequests(int userId);

    @Transactional(propagation = Propagation.REQUIRED)
    ParticipationRequestDto cancelRequest(int userId, int requestId);
}
