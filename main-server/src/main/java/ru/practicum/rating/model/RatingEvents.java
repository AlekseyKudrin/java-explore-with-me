package ru.practicum.rating.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.event.model.Event;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RatingEvents {
    Event event;
    Long like;
}
