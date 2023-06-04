package ru.practicum.reqest.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.MainHttp;

import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestMapper {

    public static Request toRequest() {

        return null;
    }

    public static ParticipationRequestDto toParticipationRequestDto(Request request) {

        return new ParticipationRequestDto(
                request.getId(),
                request.getCreated().format(MainHttp.SERVER_FORMAT),
                request.getEvent(),
                request.getRequester(),
                request.getStatus()
        );
    }
}
