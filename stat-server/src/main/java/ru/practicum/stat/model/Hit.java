package ru.practicum.stat.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "STATISTICS")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Hit {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "APP")
    String app;
    @Column(name = "URI")
    String uri;
    @Column(name = "IP")
    String ip;
    @Column(name = "TIMESTAMP")
    LocalDateTime timestamp;
}
