package ru.practicum.reqest.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.reqest.dto.ParticipationRequestDto;
import ru.practicum.util.General;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestMapper {

    public static ParticipationRequestDto toParticipationRequestDto(Request request) {

        return new ParticipationRequestDto(
                request.getId(),
                request.getCreated().format(General.SERVER_FORMAT),
                request.getEvent(),
                request.getRequester(),
                request.getStatus()
        );
    }
}
