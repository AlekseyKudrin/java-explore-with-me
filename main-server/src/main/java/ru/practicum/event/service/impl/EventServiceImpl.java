package ru.practicum.event.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.MainServer;
import ru.practicum.category.model.Category;
import ru.practicum.category.service.impl.CategoryServiceImpl;
import ru.practicum.client.ServerClient;
import ru.practicum.event.dao.EventRepository;
import ru.practicum.event.model.*;
import ru.practicum.event.model.enums.State;
import ru.practicum.event.model.enums.StateAction;
import ru.practicum.event.service.EventService;
import ru.practicum.exceptionHandler.exception.ValidateFieldException;
import ru.practicum.exceptionHandler.exception.ValueNotFoundDbException;
import ru.practicum.location.service.impl.LocationServiceImpl;
import ru.practicum.reqest.model.Request;
import ru.practicum.reqest.service.impl.RequestServiceImpl;
import ru.practicum.user.model.User;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    private final ServerClient serverClient;


    public EventFullDto createEvent(User user, NewEventDto newEventDto) {
        Category category = categoryService.findCategoryById(newEventDto.getCategory());
        locationService.createLocation(newEventDto.getLocation());
        Event event = eventRepository.save(EventMapper.toEvent(user, category, newEventDto));
//        requestService.createRequest(user, event);
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
    public EventFullDto updateEvent(Integer eventId, UpdateEventUserRequest event) {

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
        int countConfirmedRequest = requestService.getCountConfirmedRequest(updateEvent.getId());
        int views = 0;
        return EventMapper.toEventFullDto(countConfirmedRequest, views, updateEvent);
    }

    @Override
    public Event findEventById(Integer eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new ValueNotFoundDbException("Event not found"));
    }

    @Override
    public EventFullDto saveUpdateEvent(Event updateEvent) {
        long count = requestService.getCountConfirmedRequest(updateEvent.getId());
        locationService.createLocation(updateEvent.getLocation());
        return getEventFullDto(eventRepository.save(updateEvent), count);
    }

    @Override
    public List<EventShortDto> getEvents(String text, List<Integer> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size) {
        PageRequest pageRequest = PageRequest.of(from > 0 ? from / size : 0, size);
        List<EventShort> eventShort = eventRepository.findEventsByParametersOfUser(text, categories, paid, rangeStart, rangeEnd, pageRequest);
        eventShort.forEach(t -> {
            int confirmed = requestService.getCountConfirmedRequest(t.getInitiator().getId());
            t.setConfirmedRequests(confirmed);
        });
        eventShort.forEach(t -> t.setViews(0));
        if (rangeStart != null && rangeEnd != null) {
            if (rangeStart.isAfter(rangeEnd)) {
                throw new ValidateFieldException("Start date cannot be before than end date");
            }
        }
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
        ResponseEntity<Object> response = serverClient.getStats("1992-11-12 23:15:10", "3000-11-12 23:15:10", List.of("/events/" + event.getId()), false);
        int views = 0;
        ArrayList<Object> list = (ArrayList<Object>) response.getBody();
        if (list != null) {
            views = list.size();
        }
        return EventMapper.toEventFullDto(countConfirmedRequest, views, event);
    }

    public EventFullDto getEventFullDto(Event event, Long count) {
        int views = 0;
        return EventMapper.toEventFullDto(count, views, event);
    }


    public List<EventFullDto> getEvents(List<Integer> users, List<String> states, List<Integer> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {
        List<Request> requestList = requestService.getAllRequest();
        PageRequest pageRequest = PageRequest.of(from > 0 ? from / size : 0, size);
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression;
        if (users != null) {
            builder.and(QEvent.event.initiator.id.in(users));
        }
        if (states != null) {
            List<State> list = new ArrayList<>();
            states.forEach(i -> {
                for (State s : State.values()) {
                    int t = 0;
                    if (!i.equals(s.toString())) {
                        t++;
                    } else {
                        list.add(s);
                        break;
                    }
                    if (t == State.values().length)
                        throw new ValidationException("Incorrect state");
                }
            });
            builder.and(QEvent.event.state.in(list));
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
        Iterable<Event> events = eventRepository.findAll(expression, pageRequest);
        List<EventFullDto> list = new ArrayList<>();
        events.forEach(i -> list.add(getEventFullDto(i, requestList.stream().filter(c -> c.getEvent().equals(i.getId())).count())));
        return list;
    }
}
