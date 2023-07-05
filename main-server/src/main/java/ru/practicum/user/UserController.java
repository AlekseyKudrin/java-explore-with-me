package ru.practicum.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.model.EventFullDto;
import ru.practicum.event.model.EventShortDto;
import ru.practicum.event.model.NewEventDto;
import ru.practicum.event.model.UpdateEventUserRequest;
import ru.practicum.reqest.model.EventRequestStatusUpdateRequest;
import ru.practicum.reqest.model.EventRequestStatusUpdateResult;
import ru.practicum.reqest.model.ParticipationRequestDto;
import ru.practicum.user.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")

public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}/events")
    public List<EventShortDto> getEventsUser(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info("Received a request to return a collection of events user id={}", userId);
        List<EventShortDto> eventShortDtoList = userService.getEventsUser(userId, from, size);
        log.info("Request to return collection of events user id={} completed", userId);
        return eventShortDtoList;
    }

    @PostMapping("/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createEventUser(
            @PathVariable Integer userId,
            @RequestBody @Valid NewEventDto newEventDto
    ) {
        log.info("Received a request to create a event user id={}", userId);
        EventFullDto eventFullDto = userService.createEventUser(userId, newEventDto);
        log.info("Event id={} a user id={} successfully created", eventFullDto.getId(), userId);
        return eventFullDto;
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto getEventUser(
            @PathVariable Integer userId,
            @PathVariable Integer eventId
    ) {
        log.info("Received a request to return a event Id={} user id={}", eventId, userId);
        EventFullDto eventFullDto = userService.getEventUser(userId, eventId);
        log.info("Request to return a event Id={} user id={} completed", eventId, userId);
        return eventFullDto;
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto changeEventUser(
            @PathVariable Integer userId,
            @PathVariable Integer eventId,
            @RequestBody UpdateEventUserRequest updateEventUserRequest
    ) {
        log.info("Received a request to change a eventId={} user id={}", eventId, userId);
        return userService.changeEventUser(userId, eventId, updateEventUserRequest);
    }


    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getRequestsParticipationInEvent(
            @PositiveOrZero @PathVariable Integer userId,
            @PositiveOrZero @PathVariable Integer eventId
    ) {
        log.info("Received a request to return requests a eventId={} user id={}", eventId, userId);
        return userService.getRequestsParticipationInEvent(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests")
    public EventRequestStatusUpdateResult changeStatusParticipationInEvent(
            @PositiveOrZero @PathVariable Integer userId,
            @PositiveOrZero @PathVariable Integer eventId,
            @RequestBody EventRequestStatusUpdateRequest statusEvents
    ) {
        log.info("Received a request to change status a eventId={} user id={}", eventId, userId);
        return userService.changeStatusParticipationInEvent(userId, eventId, statusEvents);
    }

    @GetMapping("/{userId}/requests")
    public List<ParticipationRequestDto> getRequestUsersById(
            @PositiveOrZero @PathVariable Integer userId
    ) {
        log.info("Received a request to return applications user={}", userId);
        return userService.getParticipationRequestUser(userId);
    }

    @PostMapping("{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto createRequestParticipate(
            @PositiveOrZero @PathVariable Integer userId,
            @PositiveOrZero @RequestParam Integer eventId
    ) {
        log.info("Received a request to participate in event id={}", eventId);
        return userService.createRequestParticipate(userId, eventId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelingParticipate(
            @PathVariable Integer userId,
            @PathVariable Integer requestId
    ) {
        log.info("Received a request to canceling participate");
        return userService.cancelingParticipate(userId, requestId);
    }

}
