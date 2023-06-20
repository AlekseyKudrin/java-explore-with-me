package ru.practicum.rating.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.user.model.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RatingAuthors {
    User user;
    Long like;
}
