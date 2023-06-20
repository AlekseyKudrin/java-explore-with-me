package ru.practicum.rating.model;

import lombok.*;
import lombok.experimental.FieldDefaults;


import javax.persistence.*;

@Entity
@Table(name = "event_rating")
@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Rating {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
}
