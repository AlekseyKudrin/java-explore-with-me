package ru.practicum.event.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.category.model.Category;
import ru.practicum.location.model.Location;
import ru.practicum.user.model.User;

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
    @OneToOne
    @JoinColumn(name = "category")
    Category category;
    String description;
    LocalDateTime eventDate;
    @OneToOne
    @JoinColumn(name = "location")
    Location location;
    @ManyToOne
    @JoinColumn(name = "initiator")
    User initiator;
    Boolean paid;
    Integer participantLimit;
    Boolean requestModeration;
    String title;
    LocalDateTime createdOn;
}
