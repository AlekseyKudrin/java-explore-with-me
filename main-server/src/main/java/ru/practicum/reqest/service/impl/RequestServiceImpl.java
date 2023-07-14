package ru.practicum.reqest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.event.model.Event;
import ru.practicum.exceptionHandler.exception.LimitParticipationException;
import ru.practicum.exceptionHandler.exception.StatusParticipationException;
import ru.practicum.reqest.dto.EventRequestStatusUpdateRequest;
import ru.practicum.reqest.dto.EventRequestStatusUpdateResult;
import ru.practicum.reqest.dto.ParticipationRequestDto;
import ru.practicum.reqest.model.*;
import ru.practicum.reqest.model.Status;
import ru.practicum.reqest.repository.RequestRepository;
import ru.practicum.reqest.service.RequestService;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;

    @Override
    public ParticipationRequestDto createRequest(User user, Event event) {
        Request request = new Request();
        if (event.getRequestModeration() && event.getParticipantLimit() != 0) {
            request.setStatus(Status.PENDING);
        } else {
            request.setStatus(Status.CONFIRMED);
        }
        request.setRequester(user.getId());
        request.setEvent(event.getId());
        request.setCreated(LocalDateTime.now());
        request = requestRepository.save(request);
        return RequestMapper.toParticipationRequestDto(request);
    }

    @Override
    public int getCountConfirmedRequest(Integer eventId) {
        return requestRepository.countByEventAndStatus(eventId, Status.CONFIRMED);
    }

    @Override
    public List<ParticipationRequestDto> getRequestsParticipation(Integer userId, Integer eventId) {
        return requestRepository.findAllByEvent(eventId).stream().map(RequestMapper::toParticipationRequestDto).collect(Collectors.toList());
    }

    @Override
    public EventRequestStatusUpdateResult changeStatusParticipation(Integer userId, Integer eventId, Integer limit, EventRequestStatusUpdateRequest statusEvents) {
        List<Request> listRequest = requestRepository.findAllByEvent(eventId);

        AtomicInteger countConfirm = new AtomicInteger();
        listRequest.forEach(request -> {
            if (request.getStatus() == Status.CONFIRMED) {
                countConfirm.set(countConfirm.get() + 1);
            }
        });

        List<Request> listRequestChange = listRequest
                .stream()
                .filter(request -> {
                    for (int i : statusEvents.getRequestIds()) {
                        return request.getId() == i;
                    }
                    return false;
                })
                .collect(Collectors.toList());

        listRequestChange.forEach(
                request -> {
                    if (!request.getStatus().equals(Status.PENDING))
                        throw new StatusParticipationException("Status participant id=" + request.getId() + " not cannot be changed");
                    if (limit > countConfirm.get()) {
                        request.setStatus(Status.valueOf(statusEvents.getStatus()));
                        countConfirm.set(countConfirm.get() + 1);
                        requestRepository.save(request);
                    } else {
                        throw new LimitParticipationException("The participant limit has been reached");
                    }
                });

        List<Request> confirmed = requestRepository.findByEventAndStatus(eventId, Status.CONFIRMED);
        List<Request> rejected = requestRepository.findByEventAndStatus(eventId, Status.REJECTED);

        EventRequestStatusUpdateResult eventRequestStatusUpdateResult = new EventRequestStatusUpdateResult();
        eventRequestStatusUpdateResult.setConfirmedRequests(confirmed.stream().map(RequestMapper::toParticipationRequestDto).collect(Collectors.toList()));
        eventRequestStatusUpdateResult.setRejectedRequests(rejected.stream().map(RequestMapper::toParticipationRequestDto).collect(Collectors.toList()));
        return eventRequestStatusUpdateResult;
    }

    @Override
    public List<ParticipationRequestDto> getParticipation(Integer userId) {
        return requestRepository.findALLByRequester(userId)
                .stream()
                .map(RequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto cancelingParticipate(Integer userId, Integer requestId) {
        Request request = requestRepository.findByIdAndRequester(requestId, userId);
        request.setStatus(Status.CANCELED);
        requestRepository.save(request);
        return RequestMapper.toParticipationRequestDto(request);
    }

    @Override
    public Request validateParticipateOfUser(Integer userId, Integer eventId) {
        return requestRepository.findByEventAndRequester(eventId, userId);
    }
}
