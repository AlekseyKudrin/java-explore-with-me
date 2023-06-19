package ru.practicum.reqest.service;

import ru.practicum.event.model.Event;
import ru.practicum.reqest.model.EventRequestStatusUpdateRequest;
import ru.practicum.reqest.model.EventRequestStatusUpdateResult;
import ru.practicum.reqest.model.ParticipationRequestDto;
import ru.practicum.reqest.model.Request;
import ru.practicum.user.model.User;

import java.util.List;

public interface RequestService {

    ParticipationRequestDto createRequest(User user, Event event);

    int getCountConfirmedRequest(Integer eventId);

    List<ParticipationRequestDto> getRequestsParticipation(Integer userId, Integer eventId);

    EventRequestStatusUpdateResult changeStatusParticipation(Integer userId, Integer eventId, Integer limit, EventRequestStatusUpdateRequest statusEvents);

    List<ParticipationRequestDto> getParticipation(Integer userId);

    ParticipationRequestDto cancelingParticipate(Integer userId, Integer requestId);

    Request validateParticipateOfUser(Integer userId, Integer eventId);
}
