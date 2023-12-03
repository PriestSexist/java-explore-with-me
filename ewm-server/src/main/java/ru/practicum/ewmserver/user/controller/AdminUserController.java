package ru.practicum.ewmserver.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmserver.user.dto.NewUserRequest;
import ru.practicum.ewmserver.user.dto.UserDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

public interface AdminUserController {


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UserDto postUser(@RequestBody @Valid NewUserRequest newUserRequest);

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUser(@PathVariable @Positive int userId);

    @GetMapping
    List<UserDto> getAllUsers(@RequestParam List<Integer> ids,
                              @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                              @RequestParam(defaultValue = "10") @Positive int size);
}
