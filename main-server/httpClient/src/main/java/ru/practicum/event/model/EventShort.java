package ru.practicum.event.model;


import lombok.*;
import ru.practicum.category.model.Category;
import ru.practicum.user.model.User;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class EventShort {
    @NonNull
    Integer id;
    String annotation;
    Category category;
    Integer confirmedRequests;
    String eventDate;
    User initiator;
    Boolean paid;
    String title;
}
