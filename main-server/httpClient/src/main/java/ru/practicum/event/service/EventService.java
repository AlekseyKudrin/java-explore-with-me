package ru.practicum.event.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.event.model.EventShortDto;
import ru.practicum.event.model.UpdateEventUserRequest;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventFullDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    List<Event> getEventsUser(Integer userId, PageRequest pageRequest);

    EventFullDto getEventUser(Integer userId, Integer eventId);

    EventFullDto updateEvent(Integer eventId, UpdateEventUserRequest updateEventUserRequest);

    Event findEventbyId(Integer integer);

    EventFullDto saveUpdateEvent(Event updateEvent);

    List<EventShortDto> getEvents(String text, List<Integer> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size);

    EventFullDto findPublishedEventById(Integer eventId);
}
