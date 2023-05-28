package ru.practicum.reqest.service;

import ru.practicum.reqest.model.ParticipationRequestDto;

public interface RequestService {

    ParticipationRequestDto createRequest(Integer userId, Integer eventId);

    int getCountConfirmedRequest(Integer eventId);
}
