package ru.practicum.reqest.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestMapper {

    public static Request toRequest() {

        return null;
    }

    public static ParticipationRequestDto toParticipationRequestDto(Request request) {

        return new ParticipationRequestDto(
                request.getId(),
                request.getCreated(),
                request.getEvent(),
                request.getRequester(),
                request.getStatus()
        );
    }
}
