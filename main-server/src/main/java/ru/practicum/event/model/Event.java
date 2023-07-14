package ru.practicum.event.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.practicum.category.model.Category;
import ru.practicum.location.model.Location;
import ru.practicum.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Event {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String annotation;
    @ManyToOne
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
    LocalDateTime publishedOn;
    @Enumerated(EnumType.STRING)
    State state;
}
