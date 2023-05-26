package ru.practicum.admin.models.category;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "categories")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;
}
