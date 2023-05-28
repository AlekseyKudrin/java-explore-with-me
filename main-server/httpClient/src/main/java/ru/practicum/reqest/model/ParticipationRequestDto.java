package ru.practicum.reqest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.reqest.model.enums.Status;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class ParticipationRequestDto {
    Integer id;
    LocalDateTime created;
    Integer event;
    Integer requester;
    Status status;
}
