package ru.practicum.event.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.event.dto.UpdateEventAdminRequest;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.dto.UpdateEventUserRequest;
import ru.practicum.event.model.*;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    EventFullDto createEvent(User user, NewEventDto newEventDto);

    List<EventShortDto> getEventsUser(Integer userId, PageRequest pageRequest);

    EventFullDto getEventUser(Integer userId, Integer eventId);

    EventFullDto updateEventUser(Integer eventId, UpdateEventUserRequest event);

    EventFullDto updateEventAdmin(Integer eventId, UpdateEventAdminRequest event);

    Event findEventById(Integer integer);

    List<EventShortDto> getEvents(String text, List<Integer> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size);

    EventFullDto findPublishedEventById(Integer eventId);

    List<EventFullDto> getEvents(List<Integer> users, List<String> states, List<Integer> categories, LocalDateTime
            rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);

    EventShortDto getEventShortDtoByEventId(Integer eventId);

    List<EventFullDto> getEventFullDto(List<Event> events);
}
