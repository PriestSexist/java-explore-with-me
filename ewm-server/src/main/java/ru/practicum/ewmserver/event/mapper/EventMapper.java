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

import static ru.practicum.statdto.dto.Constants.FORMATTER;

@UtilityClass
public class EventMapper {

    public static Event createEvent(NewEventDto newEventDto, User initiator, Category category) {
        return Event.builder()
                .annotation(newEventDto.getAnnotation())
                .description(newEventDto.getDescription())
                .category(category)
                .confirmedRequests(0)
                .createdOn(LocalDateTime.now())
                .eventDate(newEventDto.getEventDate())
                .initiator(initiator)
                .location(newEventDto.getLocation())
                .paid(newEventDto.getPaid())
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.getRequestModeration())
                .state(EventState.PENDING)
                .title(newEventDto.getTitle())
                .views(0)
                .build();

    }
    public static EventShortDto createEventShortDto(Event event) {
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.createCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate().toString())
                .initiator(UserMapper.createUserShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public static EventFullDto createEventFullDto(Event event) {
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .description(event.getDescription())
                .category(CategoryMapper.createCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getCreatedOn().format(FORMATTER))
                .eventDate(event.getEventDate().format(FORMATTER))
                .initiator(UserMapper.createUserShortDto(event.getInitiator()))
                .location(event.getLocation())
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn().format(FORMATTER))
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }
}
