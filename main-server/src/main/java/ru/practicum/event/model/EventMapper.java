package ru.practicum.event.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.MainServer;
import ru.practicum.category.model.Category;
import ru.practicum.category.model.CategoryMapper;
import ru.practicum.event.model.enums.State;
import ru.practicum.user.model.User;
import ru.practicum.user.model.UserMapper;

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
                event.getCreatedOn().format(MainServer.SERVER_FORMAT),
                event.getDescription(),
                event.getEventDate().format(MainServer.SERVER_FORMAT),
                UserMapper.toUserShortDto(event.getInitiator()),
                event.getLocation(),
                event.getPaid(),
                event.getParticipantLimit(),
                event.getPublishedOn() == null ? "" : event.getPublishedOn().format(MainServer.SERVER_FORMAT),
                event.getRequestModeration(),
                event.getState().toString(),
                event.getTitle(),
                views);
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
        eventShortDto.setEventDate(eventShort.eventDate.format(MainServer.SERVER_FORMAT));
        eventShortDto.setInitiator(UserMapper.toUserShortDto(eventShort.initiator));
        eventShortDto.setTitle(eventShort.title);
        eventShortDto.setConfirmedRequests(eventShort.confirmedRequests);
        eventShortDto.setPaid(eventShort.paid);
        return eventShortDto;
    }
}
