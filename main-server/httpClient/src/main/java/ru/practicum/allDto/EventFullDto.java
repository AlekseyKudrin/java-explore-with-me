package ru.practicum.allDto;

import ru.practicum.admin.models.category.CategoryDto;

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
