package ru.practicum.reqest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.event.model.Event;
import ru.practicum.reqest.dao.RequestRepository;
import ru.practicum.reqest.model.*;
import ru.practicum.reqest.model.enums.Status;
import ru.practicum.reqest.service.RequestService;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
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
        return requestRepository.findByEventAndAndRequester(eventId, userId).stream().map(RequestMapper::toParticipationRequestDto).collect(Collectors.toList());
    }

    @Override
    public EventRequestStatusUpdateResult changeStatusParticipation(Integer userId, Integer eventId, EventRequestStatusUpdateRequest statusEvents) {
        List<Request> list = requestRepository.findRequestForChange(statusEvents.getRequestIds());
        list = list.stream().map(request -> {
            request.setStatus(Status.valueOf(statusEvents.getStatus()));
            return request;
        }).collect(Collectors.toList());
        requestRepository.saveAll(list);
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
        return RequestMapper.toParticipationRequestDto(
                requestRepository.updateCancelingParticipate(requestId, userId));
    }
}
