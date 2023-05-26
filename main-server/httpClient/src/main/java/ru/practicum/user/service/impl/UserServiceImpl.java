package ru.practicum.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.event.dao.EventRepository;
import ru.practicum.event.dao.LocationRepository;
import ru.practicum.event.model.event.Event;
import ru.practicum.event.model.event.EventFullDto;
import ru.practicum.event.model.event.EventMapper;
import ru.practicum.event.model.event.NewEventDto;
import ru.practicum.event.model.location.Location;
import ru.practicum.user.service.UserService;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final EventRepository eventRepository;

    private final LocationRepository locationRepository;
    @Override
    public EventFullDto createEventUser(Integer userId, NewEventDto newEventDto) {
        Location location = locationRepository.save(newEventDto.getLocation());
        Event event = eventRepository.save(EventMapper.toEvent(location, newEventDto));
        return EventMapper.toEventFullDto();
    }
}
