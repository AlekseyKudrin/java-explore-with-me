package ru.practicum.admin.service;

import ru.practicum.admin.model.UserDto;

import java.util.List;

public interface AdminService {
    UserDto createUser(UserDto userDto);

    List<UserDto> getUsers(List<Integer> ids, Integer from, Integer size);
}
