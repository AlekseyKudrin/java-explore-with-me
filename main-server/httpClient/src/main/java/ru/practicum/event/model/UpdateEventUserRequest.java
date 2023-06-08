package ru.practicum.event.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.event.model.enums.StateAction;
import ru.practicum.location.model.Location;

import javax.validation.constraints.Size;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@RequiredArgsConstructor
public class UpdateEventUserRequest {
    @Size(min = 20, max = 2000)
    String annotation;
    Integer category;
    @Size(min = 20, max = 7000)
    String description;
    String eventDate;
    Location location;
    Boolean paid;
    Integer participantLimit;
    Boolean requestModeration;
    StateAction stateAction;
    @Size(min = 3, max = 120)
    String title;
}
