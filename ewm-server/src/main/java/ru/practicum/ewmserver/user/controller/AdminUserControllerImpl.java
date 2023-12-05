package ru.practicum.ewmserver.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmserver.user.dto.NewUserRequest;
import ru.practicum.ewmserver.user.dto.UserDto;
import ru.practicum.ewmserver.user.service.AdminUserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/users")
@Validated
public class AdminUserControllerImpl implements AdminUserController {

    private final AdminUserService adminUserService;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto postUser(@RequestBody @Valid NewUserRequest newUserRequest) {
        log.debug("Вызван метод postUser");
        return adminUserService.postUser(newUserRequest);
    }

    @Override
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable @PositiveOrZero int userId) {
        log.debug("Вызван метод deleteUser");
        adminUserService.deleteUser(userId);
    }

    @Override
    @GetMapping
    public List<UserDto> getAllUsers(@RequestParam(defaultValue = "") List<Integer> ids,
                                     @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                     @RequestParam(defaultValue = "10") @Positive int size) {
        log.debug("Вызван метод getAllUsers");
        return adminUserService.getAllUsers(ids, from, size);
    }
}
