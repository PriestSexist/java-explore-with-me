package ru.practicum.ewmserver.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmserver.error.exception.DataConflictException;
import ru.practicum.ewmserver.error.exception.EntityNotFoundException;
import ru.practicum.ewmserver.user.dto.NewUserRequest;
import ru.practicum.ewmserver.user.dto.UserDto;
import ru.practicum.ewmserver.user.mapper.UserMapper;
import ru.practicum.ewmserver.user.model.User;
import ru.practicum.ewmserver.user.storage.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewmserver.error.constants.ErrorStrings.USER_NOT_FOUND_BY_ID;
import static ru.practicum.ewmserver.error.constants.ErrorStrings.USER_WITH_THIS_EMAIL_ALREADY_EXISTS;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository userRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserDto postUser(NewUserRequest newUserRequest) {
        if (userRepository.existsByEmail(newUserRequest.getEmail())) {
            throw new DataConflictException(USER_WITH_THIS_EMAIL_ALREADY_EXISTS);
        }
        User user = UserMapper.createUser(newUserRequest);
        return UserMapper.createUserDto(userRepository.save(user));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUser(int userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException(String.format(USER_NOT_FOUND_BY_ID, userId));
        }
        userRepository.deleteById(userId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<UserDto> getAllUsers(List<Integer> ids, int from, int size) {
        if (ids.isEmpty()) {
            PageRequest pageRequest = PageRequest.of(from > 0 ? from / size : 0, size);
            return userRepository.findAll(pageRequest).map(UserMapper::createUserDto).getContent();
        }
        return userRepository.getAllUsersById(ids).stream().map(UserMapper::createUserDto).collect(Collectors.toList());


    }
}
