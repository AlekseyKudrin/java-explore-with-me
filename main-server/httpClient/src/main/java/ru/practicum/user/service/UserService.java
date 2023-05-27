package ru.practicum.user.service;

import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventFullDto;
import ru.practicum.event.model.NewEventDto;
import ru.practicum.user.model.UserDto;

import java.util.List;

public interface UserService {

    UserDto creteUser(UserDto userDto);

    List<UserDto> getUsers(List<Integer> ids, Integer from, Integer size);

    void deleteUser(Integer userId);

    Event createEventUser(Integer userId, NewEventDto newEventDto);
}
