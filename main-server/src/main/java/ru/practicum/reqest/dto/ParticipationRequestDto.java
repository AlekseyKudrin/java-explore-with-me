package ru.practicum.reqest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.reqest.model.Status;

@AllArgsConstructor
@Getter
@Setter
public class ParticipationRequestDto {
    Integer id;
    String created;
    Integer event;
    Integer requester;
    Status status;
}
