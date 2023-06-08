package ru.practicum.event.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.category.model.Category;
import ru.practicum.category.service.impl.CategoryServiceImpl;
import ru.practicum.event.dao.EventRepository;
import ru.practicum.event.model.*;
import ru.practicum.event.model.enums.State;
import ru.practicum.event.model.enums.StateAction;
import ru.practicum.event.service.EventService;
import ru.practicum.exceptionHandler.exception.ValueNotFoundDbException;
import ru.practicum.location.service.impl.LocationServiceImpl;
import ru.practicum.reqest.service.impl.RequestServiceImpl;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


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
        locationService.createLocation(newEventDto.getLocation());
        Event event = eventRepository.save(EventMapper.toEvent(user, category, newEventDto));
        requestService.createRequest(user, event);
        int countConfirmedRequest = requestService.getCountConfirmedRequest(event.getId());
        int views = 0;
        return EventMapper.toEventFullDto(countConfirmedRequest, views, event);
    }


    public EventShortDto getEventShortDto(Event event) {
        int countConfirmedRequest = requestService.getCountConfirmedRequest(event.getId());
        int views = 0;
        return EventMapper.toEventShortDto(countConfirmedRequest, views, event);
    }

    @Override
    public List<Event> getEventsUser(Integer userId, PageRequest pageRequest) {
        return eventRepository.findAllByInitiatorId(userId, pageRequest);
    }

    @Override
    public EventFullDto getEventUser(Integer userId, Integer eventId) {
        Event event = eventRepository.findByIdAndInitiatorId(eventId, userId);
        int countConfirmedRequest = requestService.getCountConfirmedRequest(event.getId());
        int views = 0;
        return EventMapper.toEventFullDto(countConfirmedRequest, views, event);
    }


    @Override
    public EventFullDto updateEvent(Integer eventId, UpdateEventUserRequest updateEventUserRequest) {

        Event event = eventRepository.findById(eventId).orElseThrow();
        if (updateEventUserRequest.getAnnotation() != null) {
            event.setAnnotation(updateEventUserRequest.getAnnotation());
        }
        if (updateEventUserRequest.getCategory() != null) {
            event.setCategory(categoryService.findCategoryById(updateEventUserRequest.getCategory()));
        }
        if (updateEventUserRequest.getDescription() != null) {
            event.setDescription(updateEventUserRequest.getDescription());
        }
        if (updateEventUserRequest.getEventDate() != null) {
            event.setEventDate(LocalDateTime.parse(updateEventUserRequest.getEventDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        if (updateEventUserRequest.getLocation() != null) {
            event.setLocation(updateEventUserRequest.getLocation());
        }
        if (updateEventUserRequest.getPaid() != null) {
            event.setPaid(updateEventUserRequest.getPaid());
        }
        if (updateEventUserRequest.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventUserRequest.getParticipantLimit());
        }
        if (updateEventUserRequest.getRequestModeration() != null) {
            event.setRequestModeration(updateEventUserRequest.getRequestModeration());
        }
        if (updateEventUserRequest.getStateAction() != null) {
            if (updateEventUserRequest.getStateAction() == StateAction.CANCEL_REVIEW) {
                event.setState(State.CANCELED);
            } else {
                event.setState(State.PENDING);
            }
        }
        if (updateEventUserRequest.getTitle() != null) {
            event.setTitle(updateEventUserRequest.getTitle());
        }
        event = eventRepository.save(event);
        int countConfirmedRequest = requestService.getCountConfirmedRequest(event.getId());
        int views = 0;
        return EventMapper.toEventFullDto(countConfirmedRequest, views, event);
    }

    @Override
    public Event findEventbyId(Integer eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new ValueNotFoundDbException("Event not found"));
    }

    @Override
    public EventFullDto saveUpdateEvent(Event updateEvent) {
        locationService.createLocation(updateEvent.getLocation());
        return getEventFullDto(eventRepository.save(updateEvent));
    }

    @Override
    public List<EventShortDto> getEvents(String text, List<Integer> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size) {
        PageRequest pageRequest = PageRequest.of(from > 0 ? from / size : 0, size);
        List<EventShort> eventShort = eventRepository.findEventsByParametersOfUser(text, categories, paid, rangeStart, rangeEnd, pageRequest);
        eventShort.forEach(t -> {
            int confirmed = requestService.getCountConfirmedRequest(t.getInitiator().getId());
            t.setConfirmedRequests(confirmed);
        });
        eventShort.forEach(t -> {
            t.setViews(0);
        });
        if (onlyAvailable) {
            eventShort = eventShort.stream().filter(i -> i.getConfirmedRequests() <= i.getParticipantLimit()).collect(Collectors.toList());
        }
        if (sort != null && sort.equals("EVENT_DATE")) {
            eventShort.sort(Comparator.comparing(EventShort::getEventDate));
        } else {
            eventShort.sort(Comparator.comparing(EventShort::getViews));
        }

        return eventShort
                .stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto findPublishedEventById(Integer eventId) {
        Event event = eventRepository.findByIdAndState(eventId, State.PUBLISHED).orElseThrow(() -> new ValueNotFoundDbException("Event with id=" + eventId + " was not found"));
        int countConfirmedRequest = requestService.getCountConfirmedRequest(event.getId());
        int views = 0;
        return EventMapper.toEventFullDto(countConfirmedRequest, views, event);
    }

    public EventFullDto getEventFullDto(Event event) {
        int countConfirmedRequest = requestService.getCountConfirmedRequest(event.getId());
        int views = 0;
        return EventMapper.toEventFullDto(countConfirmedRequest, views, event);
    }


    public List<EventFullDto> getEvents(List<Integer> users, List<String> states, List<Integer> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {
        PageRequest pageRequest = PageRequest.of(from > 0 ? from / size : 0, size);
        List<Event> events = eventRepository.findEventsByParameters(users, states, categories, rangeStart, rangeEnd, pageRequest);
        return events.stream().map(this::getEventFullDto).collect(Collectors.toList());
    }
}
