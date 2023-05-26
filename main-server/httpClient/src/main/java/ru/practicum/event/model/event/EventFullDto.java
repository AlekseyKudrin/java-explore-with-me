package ru.practicum.event.model.event;

import ru.practicum.event.model.location.Location;
import ru.practicum.allDto.State;
import ru.practicum.allDto.UserShortDto;
import ru.practicum.category.model.CategoryDto;

public class EventFullDto {
    Integer id;
    String annotation;
    CategoryDto category;
    Integer confirmedRequests;
    String createdOn;
    String description;
    String eventDate;
    UserShortDto initiator;
    Location location;
    Boolean paid;
    Integer participantLimit;
    String publishedOn;
    Boolean requestModeration;
    State state;
    String title;
    Integer views;
}
