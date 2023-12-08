package ru.practicum.ewmserver.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewmserver.user.dto.UserShortDto;
import ru.practicum.statdto.dto.Constants;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CommentDto {
    private final int id;
    private final String text;
    private final int eventId;
    private final UserShortDto author;
    @JsonFormat(pattern = Constants.DATETIME_FORMAT)
    private final LocalDateTime created;
}
