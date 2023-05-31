package ru.practicum.event.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.event.model.UpdateEventUserRequest;
import ru.practicum.event.model.*;
import ru.practicum.event.model.enums.State;
import ru.practicum.event.model.enums.StateAction;
import ru.practicum.exceptionHandler.exception.ValueNotFoundDbException;
import ru.practicum.location.model.Location;
import ru.practicum.location.service.impl.LocationServiceImpl;
import ru.practicum.category.model.Category;
import ru.practicum.category.service.impl.CategoryServiceImpl;
import ru.practicum.event.dao.EventRepository;
import ru.practicum.event.service.EventService;
import ru.practicum.reqest.service.impl.RequestServiceImpl;
import ru.practicum.user.model.User;
import ru.practicum.user.service.impl.UserServiceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


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


    public EventShortDto getEventShortDto(Event event) {
        int countConfirmedRequest = requestService.getCountConfirmedRequest(event.getId());
        int views = 0;
        return EventMapper.toEventShortDto(countConfirmedRequest, views, event);
    }

    @Override
    public List<Event> getEventsUser(Integer userId, PageRequest pageRequest) {
        return eventRepository.findAllByInitiatorId(userId , pageRequest);
    }

    @Override
    public EventFullDto getEventUser(Integer userId, Integer eventId) {
        Event event = eventRepository.findByIdAndInitiatorId(userId, eventId);
        int countConfirmedRequest = requestService.getCountConfirmedRequest(event.getId());
        int views = 0;
        return EventMapper.toEventFullDto(countConfirmedRequest, views, event);
    }


    @Override
    public EventFullDto updateEvent(Integer eventId, UpdateEventUserRequest updateEventUserRequest) {

        Event event = eventRepository.findById(eventId).orElseThrow();
        if (updateEventUserRequest.getAnnotation() != null){
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
            if (updateEventUserRequest.getStateAction() == StateAction.CANCEL_REVIEW){
                event.setState(State.CANCELED);
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
    public Event getEvent(Integer eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new ValueNotFoundDbException("Event not found"));
    }

    @Override
    public Event save(Event updateEvent) {
        return eventRepository.save(updateEvent);
    }

    public EventFullDto getEventFullDto(Event event) {
        int countConfirmedRequest = requestService.getCountConfirmedRequest(event.getId());
        int views = 0;
        return EventMapper.toEventFullDto(countConfirmedRequest, views, event);
    }


}
