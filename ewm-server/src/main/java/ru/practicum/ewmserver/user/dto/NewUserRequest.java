package ru.practicum.ewmserver.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewmserver.error.constants.ErrorStrings;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class NewUserRequest {
    @NotBlank
    @Email
    @Size(min = 6, message = ErrorStrings.TOO_SHORT)
    @Size(max = 254, message = ErrorStrings.TOO_LONG)
    private final String email;
    @NotBlank
    @Size(min = 2, message = ErrorStrings.TOO_SHORT)
    @Size(max = 250, message = ErrorStrings.TOO_LONG)
    private final String name;
}
