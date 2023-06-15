package ru.practicum.reqest.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.reqest.model.enums.Status;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Request {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    Integer event;
    Integer requester;
    LocalDateTime created;
    @Enumerated(EnumType.STRING)
    Status status;
}
