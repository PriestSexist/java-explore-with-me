package ru.practicum.ewmserver.user.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewmserver.user.dto.NewUserRequest;
import ru.practicum.ewmserver.user.dto.UserDto;
import ru.practicum.ewmserver.user.dto.UserShortDto;
import ru.practicum.ewmserver.user.model.User;

@UtilityClass
public class UserMapper {
    public static UserDto createUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    public static User createUser(NewUserRequest newUserRequest) {
        return User.builder()
                .email(newUserRequest.getEmail())
                .name(newUserRequest.getName())
                .build();
    }

    public static UserShortDto createUserShortDto(User user) {
        return UserShortDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}
