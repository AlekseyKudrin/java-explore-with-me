package ru.practicum.event.model.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.event.model.location.Location;
import ru.practicum.user.model.User;
import ru.practicum.user.model.UserDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventMapper {
    public static Event toEvent(Location location, NewEventDto newEventDto) {
        Event event = new Event();
        event.setAnnotation(newEventDto.getAnnotation());
        event.setCategory(newEventDto.getCategory());
        event.setDescription(newEventDto.getDescription());
//        event.setEventDate(newEventDto.getEventDate());
        event.setLocation(location.getId());
        event.setPaid(newEventDto.getPaid());
        event.setParticipantLimit(newEventDto.getParticipantLimit());
        event.setRequestModeration(newEventDto.getRequestModeration());
        event.setTitle(event.getTitle());
        return event;
    }

    public static EventFullDto toEventFullDto() {

        return new EventFullDto();
    }
}
