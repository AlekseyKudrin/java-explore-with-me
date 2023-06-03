package ru.practicum.location.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "locations")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Location {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @NotNull
    Float lat;
    @NotNull
    Float lon;
}
