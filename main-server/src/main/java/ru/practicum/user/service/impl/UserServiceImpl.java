package ru.practicum.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.practicum.event.model.*;
import ru.practicum.event.model.enums.State;
import ru.practicum.event.service.impl.EventServiceImpl;
import ru.practicum.exceptionHandler.exception.ValueNotFoundDbException;
import ru.practicum.reqest.model.EventRequestStatusUpdateRequest;
import ru.practicum.reqest.model.EventRequestStatusUpdateResult;
import ru.practicum.reqest.model.ParticipationRequestDto;
import ru.practicum.reqest.service.impl.RequestServiceImpl;
import ru.practicum.user.dao.UserRepository;
import ru.practicum.user.model.NewUserRequest;
import ru.practicum.user.model.User;
import ru.practicum.user.model.UserDto;
import ru.practicum.user.model.UserMapper;
import ru.practicum.user.service.UserService;
import ru.practicum.util.General;

import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final EventServiceImpl eventService;

    private final UserRepository userRepository;

    private final RequestServiceImpl requestService;

    @Override
    public UserDto creteUser(NewUserRequest newUserRequest) {
        User user = userRepository.save(UserMapper.toUser(newUserRequest));

        return UserMapper.toUserDto(user);
    }

    @Override
    public List<UserDto> getUsers(List<Integer> ids, Integer from, Integer size) {
        return ids == null ? userRepository.findAll(General.toPage(from, size))
                .map(UserMapper::toUserDto)
                .getContent() : userRepository.findByIdIn(ids, General.toPage(from, size))
                .stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Integer userId) {
        try {
            userRepository.deleteById(userId);
        } catch (EmptyResultDataAccessException e) {
            throw new ValueNotFoundDbException("User with id=" + userId + " was not found");
        }
    }

    @Override
    public EventFullDto createEventUser(Integer userId, NewEventDto newEventDto) {
        return eventService.createEvent(findUserById(userId), newEventDto);
    }

    @Override
    public ParticipationRequestDto createRequestParticipate(Integer userId, Integer eventId) {
        User user = findUserById(userId);
        Event event = eventService.findEventById(eventId);
        if (event.getInitiator().equals(user)) {
            throw new ValidationException("The user is the initiator of the event");
        }
        if ((event.getParticipantLimit() != 0) && (!(event.getParticipantLimit() > requestService.getCountConfirmedRequest(eventId)))) {
            throw new ValidationException("Reached limit for participation in the event");
        }
        if (!event.getState().equals(State.PUBLISHED)) {
            throw new ValidationException("Event must be published");
        }
        if (requestService.validateParticipateOfUser(userId, eventId) != null) {
            throw new ValidationException("User has already created a request");
        }
        return requestService.createRequest(user, event);
    }

    @Override
    public List<EventShortDto> getEventsUser(Integer userId, Integer from, Integer size) {
        findUserById(userId);
        return eventService.getEventsUser(userId, General.toPage(from, size));
    }

    @Override
    public EventFullDto getEventUser(Integer userId, Integer eventId) {
        return eventService.getEventUser(userId, eventId);
    }

    @Override
    public EventFullDto changeEventUser(Integer userId, Integer eventId, UpdateEventUserRequest updateEventUserRequest) {
        findUserById(userId);
        Event event = eventService.findEventById(eventId);
        if (event.getState().equals(State.PUBLISHED)) {
            throw new ValidationException("The event is in the status " + event.getState());
        }
        return eventService.updateEvent(eventId, updateEventUserRequest);
    }

    @Override
    public List<ParticipationRequestDto> getRequestsParticipationInEvent(Integer userId, Integer eventId) {
        findUserById(userId);
        eventService.findEventById(eventId);
        return requestService.getRequestsParticipation(userId, eventId);
    }

    @Override
    public EventRequestStatusUpdateResult changeStatusParticipationInEvent(Integer userId, Integer eventId, EventRequestStatusUpdateRequest statusEvents) {
        findUserById(userId);
        int limit = eventService.findEventById(eventId).getParticipantLimit();
        return requestService.changeStatusParticipation(userId, eventId, limit, statusEvents);
    }

    @Override
    public List<ParticipationRequestDto> getParticipationRequestUser(Integer userId) {
        findUserById(userId);
        return requestService.getParticipation(userId);
    }

    @Override
    public ParticipationRequestDto cancelingParticipate(Integer userId, Integer requestId) {
        findUserById(userId);
        return requestService.cancelingParticipate(userId, requestId);
    }

    public User findUserById(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ValueNotFoundDbException("User with id=" + userId + " was not found"));
    }
}
