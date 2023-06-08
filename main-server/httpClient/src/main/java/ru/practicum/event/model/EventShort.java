package ru.practicum.event.model;


import lombok.*;
import ru.practicum.category.model.Category;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class EventShort {
    @NonNull
    Integer id;
    @NonNull
    String annotation;
    @NonNull
    Category category;
    @NonNull
    Integer participantLimit;
    Integer confirmedRequests;
    @NonNull
    LocalDateTime eventDate;
    @NonNull
    User initiator;
    @NonNull
    Boolean paid;
    @NonNull
    String title;
    Integer views;
}
