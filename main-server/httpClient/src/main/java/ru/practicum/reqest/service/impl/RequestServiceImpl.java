package ru.practicum.reqest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.reqest.dao.RequestRepository;
import ru.practicum.reqest.model.ParticipationRequestDto;
import ru.practicum.reqest.model.Request;
import ru.practicum.reqest.model.RequestMapper;
import ru.practicum.reqest.model.enums.Status;
import ru.practicum.reqest.service.RequestService;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;

    @Override
    public ParticipationRequestDto createRequest(Integer userId, Integer eventId) {
        Request request = requestRepository.save((new Request(0, eventId, userId, LocalDateTime.now(), Status.PENDING)));
        return RequestMapper.toParticipationRequestDto(request);
    }

    @Override
    public int getCountConfirmedRequest(Integer eventId) {
        return 0;
    }
}
