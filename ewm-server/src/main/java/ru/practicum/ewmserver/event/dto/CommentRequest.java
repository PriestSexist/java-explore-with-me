package ru.practicum.ewmserver.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewmserver.error.constants.ErrorStrings;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CommentRequest {
    @NotBlank
    @Size(min = 20, message = ErrorStrings.TOO_SHORT)
    @Size(max = 1000, message = ErrorStrings.TOO_LONG)
    private String text;
}
