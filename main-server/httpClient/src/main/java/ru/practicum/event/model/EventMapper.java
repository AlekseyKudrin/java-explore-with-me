package ru.practicum.event.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.MainHttp;
import ru.practicum.category.model.Category;
import ru.practicum.category.model.CategoryMapper;
import ru.practicum.event.model.enums.State;
import ru.practicum.location.model.Location;
import ru.practicum.user.model.User;
import ru.practicum.user.model.UserMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventMapper {
    public static Event toEvent(User user, Category category, NewEventDto newEventDto) {
        Event event = new Event();
        event.setAnnotation(newEventDto.getAnnotation());
        event.setCategory(category);
        event.setDescription(newEventDto.getDescription());
        event.setEventDate(newEventDto.getEventDate());
        event.setLocation(newEventDto.getLocation());
        event.setInitiator(user);
        event.setPaid(newEventDto.getPaid());
        event.setParticipantLimit(newEventDto.getParticipantLimit());
        event.setRequestModeration(newEventDto.getRequestModeration());
        event.setTitle(newEventDto.getTitle());
        event.setCreatedOn(LocalDateTime.now());
        event.setState(State.PENDING);
        return event;
    }

    public static EventFullDto toEventFullDto(int countConfirmedRequest, int views, Event event) {
        EventFullDto eventFullDto = new EventFullDto();
        eventFullDto.setId(event.getId());
        eventFullDto.setAnnotation(event.getAnnotation());
        eventFullDto.setCategory(CategoryMapper.toCategoryDto(event.getCategory()));
        eventFullDto.setConfirmedRequests(countConfirmedRequest);
        eventFullDto.setCreatedOn(event.getCreatedOn().toString());
        eventFullDto.setDescription(event.getDescription());
        eventFullDto.setEventDate(event.getEventDate().format(MainHttp.SERVER_FORMAT));
        eventFullDto.setInitiator(UserMapper.toUserShortDto(event.getInitiator()));
        eventFullDto.setLocation(event.getLocation());
        eventFullDto.setPaid(event.getPaid());
        eventFullDto.setParticipantLimit(event.getParticipantLimit());
        if (event.getPublishedOn() == null) {
            eventFullDto.setPublishedOn("");
        } else {
            eventFullDto.setPublishedOn(event.getPublishedOn().format(MainHttp.SERVER_FORMAT));
        }
        eventFullDto.setRequestModeration(event.getRequestModeration());
        eventFullDto.setState(event.getState().toString());
        eventFullDto.setTitle(event.getTitle());
        eventFullDto.setViews(views);
        return eventFullDto;
    }

    public static EventShortDto toEventShortDto(int countConfirmedRequest, int views, Event event) {
        EventShortDto eventShortDto = new EventShortDto();
        eventShortDto.setAnnotation(event.getAnnotation());
        eventShortDto.setCategory(CategoryMapper.toCategoryDto(event.getCategory()));
        eventShortDto.setConfirmedRequests(countConfirmedRequest);
        eventShortDto.setEventDate(event.getEventDate().toString());
        eventShortDto.setId(event.getId());
        eventShortDto.setInitiator(UserMapper.toUserShortDto(event.getInitiator()));
        eventShortDto.setPaid(event.getPaid());
        eventShortDto.setTitle(event.getTitle());
        eventShortDto.setViews(views);
        return eventShortDto;
    }

    public static EventShortDto toEventShortDto(EventShort eventShort) {
        EventShortDto eventShortDto = new EventShortDto();
        eventShortDto.setId(eventShort.id);
        eventShortDto.setAnnotation(eventShort.annotation);
        eventShortDto.setCategory(CategoryMapper.toCategoryDto(eventShort.category));
        eventShortDto.setEventDate(eventShort.eventDate.format(MainHttp.SERVER_FORMAT));
        eventShortDto.setInitiator(UserMapper.toUserShortDto(eventShort.initiator));
        eventShortDto.setTitle(eventShort.title);
        eventShortDto.setConfirmedRequests(eventShort.confirmedRequests);
        eventShortDto.setPaid(eventShort.paid);
        return eventShortDto;
    }
}
