package ru.practicum.rating.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.event.model.EventShortDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RatingEventsDto {
    EventShortDto event;
    Integer like;

}
