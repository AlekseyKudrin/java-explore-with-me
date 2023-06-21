package ru.practicum.rating.model;

import lombok.*;
import lombok.experimental.FieldDefaults;


import javax.persistence.*;


@MappedSuperclass
@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PRIVATE)
//@FieldDefaults(level = AccessLevel.PRIVATE)
public class RatingAuthors {
    Integer id;
    @Column(name = "user1")
    Integer user1;
    @Column(name = "rating")
    Integer rating;
}
