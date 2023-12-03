package ru.practicum.ewmserver.user.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmserver.user.dto.NewUserRequest;
import ru.practicum.ewmserver.user.dto.UserDto;

import java.util.List;

public interface AdminUserService {
    @Transactional(propagation = Propagation.REQUIRED)
    UserDto postUser(NewUserRequest newUserRequest);

    @Transactional(propagation = Propagation.REQUIRED)
    void deleteUser(int userId);

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    List<UserDto> getAllUsers(List<Integer> ids, int from, int size);
}
