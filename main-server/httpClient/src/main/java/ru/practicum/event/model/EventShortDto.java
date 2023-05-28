package ru.practicum.event.model;

import ru.practicum.user.model.UserShortDto;
import ru.practicum.category.model.CategoryDto;

public class EventShortDto {
    Integer id;
    String annotation;
    CategoryDto category;
    Integer confirmedRequests;
    String eventDate;
    UserShortDto initiator;
    Boolean paid;
    String title;
    Integer views;

}
