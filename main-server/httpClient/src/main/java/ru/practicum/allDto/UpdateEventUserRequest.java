package ru.practicum.allDto;

import ru.practicum.event.model.location.Location;

public class UpdateEventUserRequest {
    String annotation;
    Integer category;
    String description;
    String eventDate;
    Location location;
    Boolean paid;
    Integer participantLimit;
    Boolean requestModeration;
    StateAction stateAction;
    String title;
}
