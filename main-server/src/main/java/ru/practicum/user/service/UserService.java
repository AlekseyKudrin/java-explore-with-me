package ru.practicum.user.service;

import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.dto.UpdateEventUserRequest;
import ru.practicum.reqest.dto.EventRequestStatusUpdateRequest;
import ru.practicum.reqest.dto.EventRequestStatusUpdateResult;
import ru.practicum.reqest.dto.ParticipationRequestDto;
import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.model.User;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.dto.UserShortDto;

import java.util.List;

public interface UserService {

    UserDto creteUser(NewUserRequest newUserRequest);

    List<UserDto> getUsers(List<Integer> ids, Integer from, Integer size);

    void deleteUser(Integer userId);

    EventFullDto createEventUser(Integer userId, NewEventDto newEventDto);

    ParticipationRequestDto createRequestParticipate(Integer userId, Integer eventId);

    List<EventShortDto> getEventsUser(Integer userId, Integer from, Integer size);

    EventFullDto getEventUser(Integer userId, Integer eventId);

    EventFullDto changeEventUser(Integer userId, Integer eventId, UpdateEventUserRequest updateEventUserRequest);

    List<ParticipationRequestDto> getRequestsParticipationInEvent(Integer userId, Integer eventId);

    EventRequestStatusUpdateResult changeStatusParticipationInEvent(Integer userId, Integer eventId, EventRequestStatusUpdateRequest statusEvents);

    List<ParticipationRequestDto> getParticipationRequestUser(Integer userId);

    ParticipationRequestDto cancelingParticipate(Integer userId, Integer requestId);

    User findUserById(Integer userId);

    UserShortDto getUserShortDtoByUserId(Integer userId);
}
