package ru.practicum.admin.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.location.model.Location;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@RequiredArgsConstructor
public class UpdateEventAdminRequest {
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
