package ru.practicum.event.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.MainServer;
import ru.practicum.admin.model.UpdateEventAdminRequest;
import ru.practicum.category.model.Category;
import ru.practicum.category.service.impl.CategoryServiceImpl;
import ru.practicum.client.ServerClient;
import ru.practicum.event.dao.EventRepository;
import ru.practicum.event.model.*;
import ru.practicum.event.model.enums.State;
import ru.practicum.event.model.enums.StateAction;
import ru.practicum.event.service.EventService;
import ru.practicum.exceptionHandler.exception.InternalServerErrorException;
import ru.practicum.exceptionHandler.exception.ValidateFieldException;
import ru.practicum.exceptionHandler.exception.ValueNotFoundDbException;
import ru.practicum.location.service.impl.LocationServiceImpl;
import ru.practicum.reqest.service.impl.RequestServiceImpl;
import ru.practicum.user.model.User;
import ru.practicum.util.General;

import javax.validation.ValidationException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    private final LocationServiceImpl locationService;

    private final CategoryServiceImpl categoryService;

    private final RequestServiceImpl requestService;

    private final ServerClient serverClient;


    public EventFullDto createEvent(User user, NewEventDto newEventDto) {
        Category category = categoryService.findCategoryById(newEventDto.getCategory());
        locationService.createLocation(newEventDto.getLocation());
        Event event = eventRepository.save(EventMapper.toEvent(user, category, newEventDto));

        return EventMapper.toEventFullDto(0, 0, event);
    }

    @Override
    public List<EventShortDto> getEventsUser(Integer userId, PageRequest pageRequest) {
        List<Event> events = eventRepository.findAllByInitiatorId(userId, pageRequest);
        return getEventFullDto(events)
                .stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto getEventUser(Integer userId, Integer eventId) {
        Event event = eventRepository.findByIdAndInitiatorId(eventId, userId);

        return getEventFullDto(List.of(event)).get(0);
    }


    @Override
    public EventFullDto updateEventUser(Integer eventId, UpdateEventUserRequest event) {

        Event updateEvent = eventRepository.findById(eventId).orElseThrow();
        if (event.getAnnotation() != null) {
            if (event.getAnnotation().length() < 20 || event.getAnnotation().length() > 2000) {
                throw new ValidateFieldException("Length annotation min 20, max 7000 ");
            }
            updateEvent.setAnnotation(event.getAnnotation());
        }
        if (event.getCategory() != null) {
            updateEvent.setCategory(categoryService.findCategoryById(event.getCategory()));
        }
        if (event.getDescription() != null) {
            if (event.getDescription().length() < 20 || event.getDescription().length() > 7000) {
                throw new ValidateFieldException("Length description min 20, max 7000 ");
            }
            updateEvent.setDescription(event.getDescription());
        }
        if (event.getEventDate() != null) {
            LocalDateTime eventDate = LocalDateTime.parse(event.getEventDate(), MainServer.SERVER_FORMAT);
            if (eventDate.isBefore(LocalDateTime.now())) {
                throw new ValidateFieldException("Event date has already arrived");
            }
            updateEvent.setEventDate(eventDate);
        }
        if (event.getLocation() != null) {
            updateEvent.setLocation(event.getLocation());
        }
        if (event.getPaid() != null) {
            updateEvent.setPaid(event.getPaid());
        }
        if (event.getParticipantLimit() != null) {
            updateEvent.setParticipantLimit(event.getParticipantLimit());
        }
        if (event.getRequestModeration() != null) {
            updateEvent.setRequestModeration(event.getRequestModeration());
        }
        if (event.getStateAction() != null) {
            if (event.getStateAction() == StateAction.CANCEL_REVIEW) {
                updateEvent.setState(State.CANCELED);
            } else {
                updateEvent.setState(State.PENDING);
            }
        }
        if (event.getTitle() != null) {
            if (event.getTitle().length() < 3 || event.getTitle().length() > 120) {
                throw new ValidateFieldException("Length title min 3, max 120");
            }
            updateEvent.setTitle(event.getTitle());
        }
        updateEvent = eventRepository.save(updateEvent);

        return getEventFullDto(List.of(updateEvent)).get(0);
    }

    @Override
    public EventFullDto updateEventAdmin(Integer eventId, UpdateEventAdminRequest event) {
        Event updateEvent = findEventById(eventId);
        if (updateEvent.getEventDate().minusHours(1).isAfter(LocalDateTime.now())) {
            updateEvent.setPublishedOn(LocalDateTime.now());
        } else {
            throw new ValidationException("The event cannot be published because the edit date is earlier than the publication date");
        }
        if (event.getAnnotation() != null) {
            if (event.getAnnotation().length() < 20 || event.getAnnotation().length() > 2000) {
                throw new ValidateFieldException("Length annotation min 20, max 7000 ");
            }
            updateEvent.setAnnotation(event.getAnnotation());
        }
        if (event.getCategory() != null) {
            updateEvent.setCategory(categoryService.findCategoryById(event.getCategory()));
        }
        if (event.getDescription() != null) {
            if (event.getDescription().length() < 20 || event.getDescription().length() > 7000) {
                throw new ValidateFieldException("Length description min 20, max 7000 ");
            }
            updateEvent.setDescription(event.getDescription());
        }
        if (event.getEventDate() != null) {
            LocalDateTime eventDate = LocalDateTime.parse(event.getEventDate(), MainServer.SERVER_FORMAT);
            if (eventDate.isBefore(LocalDateTime.now())) {
                throw new ValidateFieldException("Event date has already arrived");
            }
            updateEvent.setEventDate(eventDate);
        }
        if (event.getLocation() != null) {
            updateEvent.setLocation(event.getLocation());
        }
        if (event.getPaid() != null) {
            updateEvent.setPaid(event.getPaid());
        }
        if (event.getParticipantLimit() != null) {
            updateEvent.setParticipantLimit(event.getParticipantLimit());
        }
        if (event.getRequestModeration() != null) {
            updateEvent.setRequestModeration(event.getRequestModeration());
        }
        if (event.getStateAction() != null) {
            if (event.getStateAction().equals(ru.practicum.admin.model.StateAction.PUBLISH_EVENT)) {
                if (!State.PENDING.equals(updateEvent.getState())) {
                    throw new ValidationException("Cannot publish the event because it's not in the right state: " + updateEvent.getState());
                } else {
                    updateEvent.setState(State.PUBLISHED);
                    updateEvent.setPublishedOn(LocalDateTime.now());
                }
            }
            if (event.getStateAction().equals(ru.practicum.admin.model.StateAction.REJECT_EVENT)) {
                if (State.PUBLISHED.equals(updateEvent.getState())) {
                    throw new ValidationException("Unable to cancel the event because it is in the wrong state: PUBLISHING");
                } else {
                    updateEvent.setState(State.CANCELED);
                }
            }
        }
        if (event.getTitle() != null) {
            if (event.getTitle().length() < 3 || event.getTitle().length() > 120) {
                throw new ValidateFieldException("Length title min 3, max 120");
            }
            updateEvent.setTitle(event.getTitle());
        }


        locationService.createLocation(updateEvent.getLocation());
        return getEventFullDto(List.of(updateEvent)).get(0);

    }

    @Override
    public List<EventFullDto> getEvents(List<Integer> users, List<String> states, List<Integer> categories, LocalDateTime
            rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression;

        if (users != null) builder.and(QEvent.event.initiator.id.in(users));
        if (states != null) {
            states.forEach(i -> {
                try {
                    State.valueOf(i);
                } catch (IllegalArgumentException e) {
                    throw new ValidationException("Incorrect state");
                }
            });
            builder.and(QEvent.event.state.in(states.stream().map(State::valueOf).collect(Collectors.toList())));
        }
        if (categories != null) {
            builder.and(QEvent.event.category.id.in(categories));
        }
        if (rangeStart != null) {
            builder.and(QEvent.event.eventDate.after(rangeStart));
        }
        if (rangeEnd != null) {
            builder.and(QEvent.event.eventDate.before(rangeEnd));
        }
        if (builder.getValue() == null) {
            expression = QEvent.event.isNotNull();
        } else {
            expression = Expressions.asBoolean(builder.getValue());
        }
        Page<Event> events = eventRepository.findAll(expression, General.toPage(from, size));

        return getEventFullDto(events.toList());
    }

    @Override
    public List<EventShortDto> getEvents(String text, List<Integer> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size) {
        if (rangeStart != null && rangeEnd != null) {
            if (rangeStart.isAfter(rangeEnd)) {
                throw new ValidateFieldException("Start date cannot be before than end date");
            }
        }

        BooleanBuilder builder = new BooleanBuilder();
        if (text != null) builder.and(QEvent.event.annotation.likeIgnoreCase("%" + text.toLowerCase() + "%"))
                .or(QEvent.event.title.likeIgnoreCase("%" + text.toLowerCase() + "%"));
        if (categories != null) builder.and(QEvent.event.category.id.in(categories));
        if (paid != null) builder.and(QEvent.event.paid.eq(paid));
        if (rangeStart != null) builder.and(QEvent.event.eventDate.after(rangeStart));
        if (rangeEnd != null) builder.and(QEvent.event.eventDate.before(rangeEnd));

        BooleanExpression expression = builder.getValue() == null ? QEvent.event.isNotNull() : Expressions.asBoolean(builder.getValue());
        Page<Event> events = eventRepository.findAll(expression, General.toPage(from, size));
        List<EventFullDto> eventFull = getEventFullDto(events.toList());

        if (onlyAvailable) {
            eventFull = eventFull.stream().filter(i -> i.getConfirmedRequests() <= i.getParticipantLimit()).collect(Collectors.toList());
        }
        if (sort != null && sort.equals("EVENT_DATE")) {
            eventFull.sort(Comparator.comparing(EventFullDto::getEventDate));
        } else {
            eventFull.sort(Comparator.comparing(EventFullDto::getViews));
        }

        return eventFull.stream().map(EventMapper::toEventShortDto).collect(Collectors.toList());
    }

    @Override
    public EventFullDto findPublishedEventById(Integer eventId) {
        Event event = eventRepository.findByIdAndState(eventId, State.PUBLISHED).orElseThrow(() -> new ValueNotFoundDbException("Event with id=" + eventId + " was not found"));

        return getEventFullDto(List.of(event)).get(0);
    }

    public Event findEventById(Integer eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new ValueNotFoundDbException("Event not found"));
    }

    public List<EventFullDto> getEventFullDto(List<Event> events) {
        if (events.size() == 0) return List.of();
        Map<Integer, Integer> views = getViews(events.stream()
                .map(Event::getId)
                .collect(Collectors.toList()));
        return events.stream()
                .map(i -> EventMapper.toEventFullDto(
                        requestService.getCountConfirmedRequest(i.getId()),
                        views.size() == 0 ? 0 : views.getOrDefault(i.getId(), 0),
                        i))
                .collect(Collectors.toList());
    }

    private Map<Integer, Integer> getViews(List<Integer> event) {
        Gson gson = new Gson();
        Stats[] views;
        ObjectMapper objectMapper = new ObjectMapper();
        Map<Integer, Integer> viewsMap = new HashMap<>();
        List<String> uris = event.stream().map(i -> "/events/" + i).collect(Collectors.toList());
        ResponseEntity<Object> response = serverClient.getStats(General.MIN_TIME, General.MAX_TIME, uris, true);

        String responseJson = gson.toJson(response.getBody());
        try {
            views = objectMapper.readValue(responseJson, Stats[].class);
        } catch (IOException e) {
            throw new InternalServerErrorException("Internal Server Error: unable to read statistics server data");
        }
        for (Stats view : views) {
            String[] lines = view.getUri().split("/");
            int a = Integer.parseInt(lines[2]);
            viewsMap.put(a, view.getHits());
        }

        return viewsMap;
    }
}