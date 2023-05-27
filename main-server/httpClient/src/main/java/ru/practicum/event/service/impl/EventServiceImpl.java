package ru.practicum.event.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.event.model.Event;
import ru.practicum.location.model.Location;
import ru.practicum.location.service.impl.LocationServiceImpl;
import ru.practicum.category.model.Category;
import ru.practicum.category.service.impl.CategoryServiceImpl;
import ru.practicum.event.dao.EventRepository;
import ru.practicum.event.model.EventFullDto;
import ru.practicum.event.model.EventMapper;
import ru.practicum.event.model.NewEventDto;
import ru.practicum.event.service.EventService;
import ru.practicum.user.model.User;


@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    private final LocationServiceImpl locationService;

    private final CategoryServiceImpl categoryService;

    public Event createEvent(User user, NewEventDto newEventDto) {
        Category category = categoryService.findCategoryById(newEventDto.getCategory());
        Location location = locationService.createLocation(newEventDto.getLocation());
        Event event = EventMapper.toEvent(user, category, location, newEventDto);
        eventRepository.save(event);
        return event;
    }


}
