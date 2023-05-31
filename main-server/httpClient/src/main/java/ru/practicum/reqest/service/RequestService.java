package ru.practicum.reqest.service;

import ru.practicum.reqest.model.EventRequestStatusUpdateRequest;
import ru.practicum.reqest.model.EventRequestStatusUpdateResult;
import ru.practicum.reqest.model.ParticipationRequestDto;

import java.util.List;

public interface RequestService {

    ParticipationRequestDto createRequest(Integer userId, Integer eventId);

    int getCountConfirmedRequest(Integer eventId);

    List<ParticipationRequestDto> getRequestsParticipation(Integer userId, Integer eventId);

    EventRequestStatusUpdateResult changeStatusParticipation(Integer userId, Integer eventId, EventRequestStatusUpdateRequest statusEvents);

    ParticipationRequestDto getParticipation(Integer userId);

    ParticipationRequestDto cancelingParticipate(Integer userId, Integer requestId);
}
