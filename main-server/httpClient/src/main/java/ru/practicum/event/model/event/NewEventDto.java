package ru.practicum.event.model.event;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.event.model.location.Location;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@RequiredArgsConstructor
public class NewEventDto {
    String annotation;
    Integer category;
    String description;
    LocalDateTime eventDate;
    Location location;
    Boolean paid;
    Integer participantLimit;
    Boolean requestModeration;
    String string;
}
