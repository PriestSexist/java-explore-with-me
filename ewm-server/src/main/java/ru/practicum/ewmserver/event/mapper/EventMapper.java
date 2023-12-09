package ru.practicum.ewmserver.event.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewmserver.category.mapper.CategoryMapper;
import ru.practicum.ewmserver.category.model.Category;
import ru.practicum.ewmserver.event.dto.EventFullDto;
import ru.practicum.ewmserver.event.dto.EventShortDto;
import ru.practicum.ewmserver.event.dto.NewEventDto;
import ru.practicum.ewmserver.event.model.Event;
import ru.practicum.ewmserver.event.model.EventState;
import ru.practicum.ewmserver.user.mapper.UserMapper;
import ru.practicum.ewmserver.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

@UtilityClass
public class EventMapper {

    public static Event createEvent(NewEventDto newEventDto, User initiator, Category category) {
        return Event.builder()
                .annotation(newEventDto.getAnnotation())
                .description(newEventDto.getDescription())
                .category(category)
                .createdOn(LocalDateTime.now())
                .eventDate(newEventDto.getEventDate())
                .initiator(initiator)
                .location(newEventDto.getLocation())
                .paid(newEventDto.getPaid())
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.getRequestModeration())
                .state(EventState.PENDING)
                .title(newEventDto.getTitle())
                .build();

    }

    public static EventShortDto createEventShortDto(Event event, int confirmedRequests) {
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.createCategoryDto(event.getCategory()))
                .confirmedRequests(confirmedRequests)
                .eventDate(event.getEventDate())
                .initiator(UserMapper.createUserShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public static EventFullDto createEventFullDto(Event event, int confirmedRequests) {
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .description(event.getDescription())
                .category(CategoryMapper.createCategoryDto(event.getCategory()))
                .confirmedRequests(confirmedRequests)
                .createdOn(event.getCreatedOn())
                .eventDate(event.getEventDate())
                .initiator(UserMapper.createUserShortDto(event.getInitiator()))
                .location(event.getLocation())
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .views(event.getViews())
                .publishedOn(event.getPublishedOn())
                .comments((event.getComments() == null) ? new ArrayList<>() : event.getComments().stream().map(CommentMapper::createCommentDto).collect(Collectors.toList()) )
                .build();
    }
}
