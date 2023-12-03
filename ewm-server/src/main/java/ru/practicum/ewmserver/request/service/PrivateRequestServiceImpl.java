package ru.practicum.ewmserver.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmserver.error.exception.DataConflictException;
import ru.practicum.ewmserver.error.exception.EntityNotFoundException;
import ru.practicum.ewmserver.error.exception.ForbiddenOperationException;
import ru.practicum.ewmserver.event.model.Event;
import ru.practicum.ewmserver.event.model.EventState;
import ru.practicum.ewmserver.event.storage.EventRepository;
import ru.practicum.ewmserver.request.mapper.RequestMapper;
import ru.practicum.ewmserver.request.model.Request;
import ru.practicum.ewmserver.request.model.RequestStatus;
import ru.practicum.ewmserver.request.dto.ParticipationRequestDto;
import ru.practicum.ewmserver.request.storage.RequestRepository;
import ru.practicum.ewmserver.user.model.User;
import ru.practicum.ewmserver.user.storage.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewmserver.error.constants.ErrorStrings.*;

@Service
@RequiredArgsConstructor
public class PrivateRequestServiceImpl implements PrivateRequestService{

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ParticipationRequestDto postRequest(int userId, int eventId) {

        User userFromDb = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND_BY_ID, userId)));
        Event eventFromDb = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException(String.format(EVENT_NOT_FOUND_BY_ID, eventId)));

        if (requestRepository.existsByRequesterIdAndEventId(userId, eventId)) {
            throw new DataConflictException(String.format(REQUEST_ALREADY_EXISTS, userId, eventId));
        }

        if (userId == eventFromDb.getInitiator().getId()) {
            throw new DataConflictException(String.format(REQUEST_FROM_OWNER, userId, eventId));
        }

        if (eventFromDb.getState().equals(EventState.PUBLISHED)) {
            throw new DataConflictException(String.format(REQUEST_FOR_NOT_PUBLISHED_EVENT,  eventId));
        }

        if (eventFromDb.getParticipantLimit() > 0) {
            if (requestRepository.countRequestByEventIdAndStatus(eventId, RequestStatus.CONFIRMED) >= eventFromDb.getParticipantLimit()) {
                throw new DataConflictException(String.format(EVENT_IS_FULL,  eventId));
            }
        }

        Request request = RequestMapper.createRequest(userFromDb, eventFromDb);

        return RequestMapper.createParticipationRequestDto(requestRepository.save(request));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<ParticipationRequestDto> getRequests(int userId) {

        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException(String.format(USER_NOT_FOUND_BY_ID, userId));
        }

        return requestRepository.getRequestsByRequesterId(userId).stream()
                .map(RequestMapper::createParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ParticipationRequestDto cancelRequest(int userId, int requestId) {

        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException(String.format(USER_NOT_FOUND_BY_ID, userId));
        }

        Request request = requestRepository.findById(requestId).orElseThrow(() -> new EntityNotFoundException(String.format(REQUEST_NOT_FOUND_BY_ID, requestId)));

        if (request.getRequester().getId() != userId) {
            throw new ForbiddenOperationException(CANT_CANCEL_NOT_OWNER);
        }

        request.setStatus(RequestStatus.CANCELED);

        return RequestMapper.createParticipationRequestDto(requestRepository.save(request));
    }
}
