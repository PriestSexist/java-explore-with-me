package ru.practicum.ewmserver.event.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewmserver.event.dto.CommentDto;
import ru.practicum.ewmserver.event.dto.CommentRequest;
import ru.practicum.ewmserver.event.model.Comment;
import ru.practicum.ewmserver.event.model.Event;
import ru.practicum.ewmserver.user.mapper.UserMapper;
import ru.practicum.ewmserver.user.model.User;

import java.time.LocalDateTime;

@UtilityClass
public class CommentMapper {
    public static Comment createComment(CommentRequest commentRequest, Event event, User author) {
        return Comment.builder()
                .text(commentRequest.getText())
                .event(event)
                .author(author)
                .created(LocalDateTime.now())
                .build();
    }

    public static CommentDto createCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .eventId(comment.getEvent().getId())
                .author(UserMapper.createUserShortDto(comment.getAuthor()))
                .created(LocalDateTime.now())
                .build();
    }
}
