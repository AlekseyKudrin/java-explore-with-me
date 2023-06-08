package ru.practicum.reqest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.event.model.Event;
import ru.practicum.event.service.impl.EventServiceImpl;
import ru.practicum.exceptionHandler.exception.LimitParticipationException;
import ru.practicum.exceptionHandler.exception.StatusParticipationException;
import ru.practicum.exceptionHandler.exception.ValueNotFoundDbException;
import ru.practicum.reqest.dao.RequestRepository;
import ru.practicum.reqest.model.*;
import ru.practicum.reqest.model.enums.Status;
import ru.practicum.reqest.service.RequestService;
import ru.practicum.user.model.User;

import javax.transaction.Transactional;
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
        if (event.getRequestModeration()) {
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
        return requestRepository.findAllByEventAndRequester(eventId, userId).stream().map(RequestMapper::toParticipationRequestDto).collect(Collectors.toList());
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
                        throw new StatusParticipationException("Status participant id=" + request.getId()+ " not cannot be changed");
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
    @Transactional
    public ParticipationRequestDto cancelingParticipate(Integer userId, Integer requestId) {
        requestRepository.updateCancelingParticipate(requestId, userId);
        return RequestMapper.toParticipationRequestDto(findRequest(requestId));
    }

    public Request findRequest(Integer requestId) {
        return requestRepository.findById(requestId).orElseThrow(() -> new ValueNotFoundDbException("Request with id=" + requestId + " was not found"));
    }
}
