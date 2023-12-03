package ru.practicum.ewmserver.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UserDto {
    private final int id;
    @Email
    private final String email;
    @NotBlank
    private final String name;
}
