package ru.practicum.event.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.category.model.Category;
import ru.practicum.category.model.CategoryMapper;
import ru.practicum.event.model.enums.State;
import ru.practicum.user.model.User;
import ru.practicum.user.model.UserMapper;
import ru.practicum.util.General;

import java.time.LocalDateTime;

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

    public static EventFullDto toEventFullDto(long countConfirmedRequest, int views, Event event) {
        return new EventFullDto(
                event.getId(),
                event.getAnnotation(),
                CategoryMapper.toCategoryDto(event.getCategory()),
                (int) countConfirmedRequest,
                event.getCreatedOn().format(General.SERVER_FORMAT),
                event.getDescription(),
                event.getEventDate().format(General.SERVER_FORMAT),
                UserMapper.toUserShortDto(event.getInitiator()),
                event.getLocation(),
                event.getPaid(),
                event.getParticipantLimit(),
                event.getPublishedOn() == null ? "" : event.getPublishedOn().format(General.SERVER_FORMAT),
                event.getRequestModeration(),
                event.getState().toString(),
                event.getTitle(),
                views);
    }

    public static EventShortDto toEventShortDto(EventFullDto eventShort) {
        EventShortDto eventShortDto = new EventShortDto();
        eventShortDto.setId(eventShort.getId());
        eventShortDto.setAnnotation(eventShort.getAnnotation());
        eventShortDto.setCategory(eventShort.getCategory());
        eventShortDto.setEventDate(eventShort.getEventDate());
        eventShortDto.setInitiator(eventShort.getInitiator());
        eventShortDto.setTitle(eventShort.getTitle());
        eventShortDto.setConfirmedRequests(eventShort.getConfirmedRequests());
        eventShortDto.setPaid(eventShort.getPaid());
        return eventShortDto;
    }
}
