package ru.practicum.ewmserver.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmserver.category.model.Category;
import ru.practicum.ewmserver.category.storage.CategoryRepository;
import ru.practicum.ewmserver.error.exception.DataConflictException;
import ru.practicum.ewmserver.error.exception.EntityNotFoundException;
import ru.practicum.ewmserver.error.exception.InvalidRequestException;
import ru.practicum.ewmserver.event.dto.*;
import ru.practicum.ewmserver.event.mapper.CommentMapper;
import ru.practicum.ewmserver.event.mapper.EventMapper;
import ru.practicum.ewmserver.event.model.Comment;
import ru.practicum.ewmserver.event.model.Event;
import ru.practicum.ewmserver.event.model.EventState;
import ru.practicum.ewmserver.event.storage.CommentRepository;
import ru.practicum.ewmserver.event.storage.EventRepository;
import ru.practicum.ewmserver.request.dto.ParticipationRequestDto;
import ru.practicum.ewmserver.request.mapper.RequestMapper;
import ru.practicum.ewmserver.request.model.Request;
import ru.practicum.ewmserver.request.model.RequestStatus;
import ru.practicum.ewmserver.request.storage.RequestRepository;
import ru.practicum.ewmserver.user.model.User;
import ru.practicum.ewmserver.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewmserver.error.constants.ErrorStrings.*;

@Service
@RequiredArgsConstructor
public class PrivateEventServiceImpl implements PrivateEventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final RequestRepository requestRepository;
    private final CommentRepository commentRepository;


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public EventFullDto postEvent(NewEventDto newEventDto, int userId) {

        if (LocalDateTime.now().plusHours(2).isAfter(newEventDto.getEventDate())) {
            throw new InvalidRequestException(EVENT_DATE_2_HOURS_MIN_SHOULD_BE + newEventDto.getEventDate());
        }

        User userFromDb = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND_BY_ID, userId)));
        Category categoryFromDb = categoryRepository.findById(newEventDto.getCategory()).orElseThrow(() -> new EntityNotFoundException(String.format(CATEGORY_NOT_FOUND_BY_ID, newEventDto.getCategory())));

        Event event = EventMapper.createEvent(newEventDto, userFromDb, categoryFromDb);

        Event eventFromDb = eventRepository.save(event);

        return EventMapper.createEventFullDto(eventFromDb, requestRepository.countRequestByEventIdAndStatus(eventFromDb.getId(), RequestStatus.CONFIRMED));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<EventShortDto> getUserEvents(int userId, int from, int size) {

        PageRequest pageRequest = PageRequest.of(from > 0 ? from / size : 0, size);

        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException(String.format(USER_NOT_FOUND_BY_ID, userId));
        }

        return eventRepository.getByInitiatorId(userId, pageRequest).stream()
                .map(event -> EventMapper.createEventShortDto(event, requestRepository.countRequestByEventIdAndStatus(event.getId(), RequestStatus.CONFIRMED)))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public EventFullDto getEventById(int userId, int eventId) {

        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException(String.format(USER_NOT_FOUND_BY_ID, userId));
        }

        Event eventFromDb = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException(String.format(EVENT_NOT_FOUND_BY_ID, eventId)));

        return EventMapper.createEventFullDto(eventFromDb, requestRepository.countRequestByEventIdAndStatus(eventFromDb.getId(), RequestStatus.CONFIRMED));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public EventFullDto patchEvent(UpdateEventUserRequest updateEventUserRequest, int userId, int eventId) {

        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException(String.format(USER_NOT_FOUND_BY_ID, userId));
        }

        Event eventFromDb = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException(String.format(EVENT_NOT_FOUND_BY_ID, eventId)));

        if (eventFromDb.getState().equals(EventState.PUBLISHED)) {
            throw new DataConflictException(PATCH_NOT_PENDING_STATE);
        }

        if (updateEventUserRequest.getStateAction() != null) {
            switch (updateEventUserRequest.getStateAction()) {
                case CANCEL_REVIEW:
                    eventFromDb.setState(EventState.CANCELED);
                    break;
                case SEND_TO_REVIEW:
                    eventFromDb.setState(EventState.PENDING);
                    break;
                default:
                    throw new InvalidRequestException(INVALID_ACTION + eventFromDb);
            }
        }

        if (updateEventUserRequest.getEventDate() != null) {
            if (LocalDateTime.now().plusHours(2).isAfter(updateEventUserRequest.getEventDate())) {
                throw new InvalidRequestException(EVENT_DATE_2_HOURS_MIN_SHOULD_BE);
            }
            eventFromDb.setEventDate(updateEventUserRequest.getEventDate());
        }

        if (updateEventUserRequest.getAnnotation() != null) {
            eventFromDb.setAnnotation(updateEventUserRequest.getAnnotation());
        }

        if (updateEventUserRequest.getCategory() != null && updateEventUserRequest.getCategory() != (eventFromDb.getCategory().getId())) {
            Category category = categoryRepository.findById(updateEventUserRequest.getCategory()).orElseThrow(() -> new EntityNotFoundException(String.format(CATEGORY_NOT_FOUND_BY_ID, updateEventUserRequest.getCategory())));
            eventFromDb.setCategory(category);
        }

        if (updateEventUserRequest.getDescription() != null) {
            eventFromDb.setDescription(updateEventUserRequest.getDescription());
        }

        if (updateEventUserRequest.getLocation() != null) {
            eventFromDb.setLocation(updateEventUserRequest.getLocation());
        }

        if (updateEventUserRequest.getPaid() != null) {
            eventFromDb.setPaid(updateEventUserRequest.getPaid());
        }

        if (updateEventUserRequest.getRequestModeration() != null) {
            eventFromDb.setRequestModeration(updateEventUserRequest.getRequestModeration());
        }

        if (updateEventUserRequest.getTitle() != null) {
            eventFromDb.setTitle(updateEventUserRequest.getTitle());
        }

        if (updateEventUserRequest.getParticipantLimit() != null) {
            eventFromDb.setParticipantLimit(updateEventUserRequest.getParticipantLimit());
        }


        return EventMapper.createEventFullDto(eventRepository.save(eventFromDb), requestRepository.countRequestByEventIdAndStatus(eventFromDb.getId(), RequestStatus.CONFIRMED));

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<ParticipationRequestDto> getRequestsInEvent(int userId, int eventId) {

        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException(String.format(USER_NOT_FOUND_BY_ID, userId));
        }

        if (!eventRepository.existsById(eventId)) {
            throw new EntityNotFoundException(String.format(EVENT_NOT_FOUND_BY_ID, eventId));
        }

        return requestRepository.getRequestsByEventId(eventId).stream()
                .map(RequestMapper::createParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public EventRequestStatusUpdateResult patchRequests(EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest, int userId, int eventId) {

        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException(String.format(USER_NOT_FOUND_BY_ID, userId));
        }

        if (!eventRepository.existsById(eventId)) {
            throw new EntityNotFoundException(String.format(EVENT_NOT_FOUND_BY_ID, eventId));
        }

        List<Request> requests = requestRepository.findAllById(eventRequestStatusUpdateRequest.getRequestIds());

        switch (eventRequestStatusUpdateRequest.getStatus()) {
            case REJECTED:
                for (Request request : requests) {
                    if (request.getStatus() == RequestStatus.CONFIRMED) {
                        throw new DataConflictException("Cannot reject an already confirmed request.");
                    }
                    request.setStatus(RequestStatus.REJECTED);
                }
                break;
            case CONFIRMED:
                for (Request request : requests) {

                    if (!request.getEvent().getRequestModeration() || request.getEvent().getParticipantLimit() == 0) {
                        request.setStatus(RequestStatus.CONFIRMED);
                        continue;
                    }

                    if (requestRepository.countRequestByEventIdAndStatus(request.getEvent().getId(), RequestStatus.CONFIRMED) >= request.getEvent().getParticipantLimit()) {
                        request.setStatus(RequestStatus.REJECTED);
                        throw new DataConflictException("Cant accept request.");
                    }

                    request.setStatus(RequestStatus.CONFIRMED);

                }
                break;
        }

        return RequestMapper.createEventRequestStatusUpdateResult(requestRepository.saveAll(requests));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CommentDto postComment(CommentRequest commentRequest, int userId, int eventId) {

        User userFromDb = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND_BY_ID, userId)));
        Event eventFromDb = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException(String.format(EVENT_NOT_FOUND_BY_ID, eventId)));

        if (!eventFromDb.getState().equals(EventState.PUBLISHED)) {
            throw new DataConflictException(COMMENT_NOT_PUBLISHED_EVENT);
        }

        Comment comment = CommentMapper.createComment(commentRequest, eventFromDb, userFromDb);

        return CommentMapper.createCommentDto(commentRepository.save(comment));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CommentDto patchComment(CommentRequest commentRequest, int userId, int eventId, int commentId) {

        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException(String.format(USER_NOT_FOUND_BY_ID, userId));
        }

        if (!eventRepository.existsById(eventId)) {
            throw new EntityNotFoundException(String.format(EVENT_NOT_FOUND_BY_ID, eventId));
        }

        Comment commentFromDb = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException(String.format(COMMENT_NOT_FOUND_BY_ID, commentId)));

        if (commentFromDb.getAuthor().getId() != userId) {
            throw new DataConflictException(String.format(NOT_OWNER_CHANGES_COMMENT, userId, commentId));
        }

        if (commentRequest.getText() != null) {
            commentFromDb.setText(commentRequest.getText());
        }

        return CommentMapper.createCommentDto(commentRepository.save(commentFromDb));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteComment(int userId, int eventId, int commentId) {

        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException(String.format(USER_NOT_FOUND_BY_ID, userId));
        }

        if (!eventRepository.existsById(eventId)) {
            throw new EntityNotFoundException(String.format(EVENT_NOT_FOUND_BY_ID, eventId));
        }

        Comment commentFromDb = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException(String.format(COMMENT_NOT_FOUND_BY_ID, commentId)));

        if (commentFromDb.getAuthor().getId() != userId) {
            throw new DataConflictException(String.format(NOT_OWNER_CHANGES_COMMENT, userId, commentId));
        }

        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<CommentDto> getComments(int userId, int eventId, int from, int size, boolean forCurrentUser) {

        PageRequest pageRequest = PageRequest.of(from > 0 ? from / size : 0, size);
        Page<Comment> comments;

        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException(String.format(USER_NOT_FOUND_BY_ID, userId));
        }

        if (!eventRepository.existsById(eventId)) {
            throw new EntityNotFoundException(String.format(EVENT_NOT_FOUND_BY_ID, eventId));
        }

        if (forCurrentUser) {
            comments = commentRepository.getAllByAuthorId(userId, pageRequest);
        } else {
            comments = commentRepository.findAll(pageRequest);
        }
        return comments.stream()
                .map(CommentMapper::createCommentDto)
                .collect(Collectors.toList());
    }
}
