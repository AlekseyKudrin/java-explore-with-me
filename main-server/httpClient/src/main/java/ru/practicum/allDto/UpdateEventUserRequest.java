package ru.practicum.allDto;

import ru.practicum.location.model.Location;

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
