package ru.practicum.admin.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.admin.dao.UserRepository;
import ru.practicum.admin.model.User;
import ru.practicum.admin.model.UserDto;
import ru.practicum.admin.model.UserMapper;
import ru.practicum.admin.service.AdminService;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userRepository.save(UserMapper.toUser(userDto));
        log.info("User successfully created");
        return UserMapper.toUserDto(user);
    }

    @Override
    public List<UserDto> getUsers(List<Integer> ids, Integer from, Integer size) {
        if (ids == null) {
            return null;
            userRepository.getUsersSelectionOptions(from, size);
        } else {
            return userRepository.findByIdIn(ids).stream().map(UserMapper::toUserDto).collect(Collectors.toList());
        }
    }
}
