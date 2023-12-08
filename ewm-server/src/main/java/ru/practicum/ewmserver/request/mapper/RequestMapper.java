package ru.practicum.ewmserver.request.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewmserver.event.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewmserver.event.model.Event;
import ru.practicum.ewmserver.request.dto.ParticipationRequestDto;
import ru.practicum.ewmserver.request.model.Request;
import ru.practicum.ewmserver.request.model.RequestStatus;
import ru.practicum.ewmserver.user.model.User;
import ru.practicum.statdto.dto.Constants;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class RequestMapper {
    public static Request createRequest(User requester, Event event) {
        Request request = Request.builder()
                .requester(requester)
                .created(LocalDateTime.now())
                .event(event)
                .build();

        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            request.setStatus(RequestStatus.CONFIRMED);
        } else {
            request.setStatus(RequestStatus.PENDING);
        }

        return request;
    }

    public static ParticipationRequestDto createParticipationRequestDto(Request request) {
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .event(request.getEvent().getId())
                .created(request.getCreated().format(Constants.FORMATTER))
                .requester(request.getRequester().getId())
                .status(request.getStatus().toString())
                .build();
    }

    public static EventRequestStatusUpdateResult createEventRequestStatusUpdateResult(List<Request> requests) {
        return EventRequestStatusUpdateResult.builder()
                .confirmedRequests(requests.stream().filter(request -> request.getStatus() == RequestStatus.CONFIRMED).map(RequestMapper::createParticipationRequestDto).collect(Collectors.toList()))
                .rejectedRequests(requests.stream().filter(request -> request.getStatus() == RequestStatus.REJECTED).map(RequestMapper::createParticipationRequestDto).collect(Collectors.toList()))
                .build();
    }
}
