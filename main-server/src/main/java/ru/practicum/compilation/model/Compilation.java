package ru.practicum.compilation.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "compilations")
@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Compilation {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @ElementCollection
    @CollectionTable(name = "list_events", joinColumns = @JoinColumn(name = "id"))
    List<Integer> events;
    Boolean pinned;
    String title;
}
