package ru.practicum.event.model.location;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "locations")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Location {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    Float lat;
    Float lon;
}
