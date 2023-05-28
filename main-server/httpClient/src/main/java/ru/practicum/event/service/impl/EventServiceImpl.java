package ru.practicum.event.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.event.model.*;
import ru.practicum.location.model.Location;
import ru.practicum.location.service.impl.LocationServiceImpl;
import ru.practicum.category.model.Category;
import ru.practicum.category.service.impl.CategoryServiceImpl;
import ru.practicum.event.dao.EventRepository;
import ru.practicum.event.service.EventService;
import ru.practicum.reqest.service.impl.RequestServiceImpl;
import ru.practicum.user.model.User;


@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    private final LocationServiceImpl locationService;

    private final CategoryServiceImpl categoryService;

    private final RequestServiceImpl requestService;

    public EventFullDto createEvent(User user, NewEventDto newEventDto) {
        Category category = categoryService.findCategoryById(newEventDto.getCategory());
        Location location = locationService.createLocation(newEventDto.getLocation());
        Event event = eventRepository.save(EventMapper.toEvent(user, category, location, newEventDto));
        int countConfirmedRequest = requestService.getCountConfirmedRequest(event.getId());
        int views = 0;
        return EventMapper.toEventFullDto(countConfirmedRequest, views, event);
    }


    public EventShortDto getEventShort(int id) {
        return null;
    }
}
