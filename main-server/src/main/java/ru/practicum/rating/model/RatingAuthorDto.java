package ru.practicum.rating.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.user.model.UserDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RatingAuthorDto {
    UserDto userDto;
    Long like;
}
