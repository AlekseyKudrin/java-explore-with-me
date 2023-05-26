package ru.practicum.user.service;

import ru.practicum.event.model.event.EventFullDto;
import ru.practicum.event.model.event.NewEventDto;

public interface UserService {
    EventFullDto createEventUser(Integer userId, NewEventDto newEventDto);
}
