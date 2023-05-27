package ru.practicum.reqest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.reqest.dao.RequestRepository;
import ru.practicum.reqest.service.RequestService;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository locationRepository;

    }
