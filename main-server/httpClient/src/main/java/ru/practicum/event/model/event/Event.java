package ru.practicum.event.model.event;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Event {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String annotation;
    Integer category;
    String description;
    LocalDateTime eventDate;
    Integer location;
    Boolean paid;
    Integer participantLimit;
    Boolean requestModeration;
    String title;
}
