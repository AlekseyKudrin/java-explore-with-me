package ru.practicum.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.reqest.model.EventRequestStatusUpdateRequest;
import ru.practicum.reqest.model.EventRequestStatusUpdateResult;
import ru.practicum.event.model.EventFullDto;
import ru.practicum.event.model.EventShortDto;
import ru.practicum.event.model.NewEventDto;
import ru.practicum.event.model.UpdateEventUserRequest;
import ru.practicum.reqest.model.ParticipationRequestDto;
import ru.practicum.user.service.impl.UserServiceImpl;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping("/{userId}/events")
    public List<EventShortDto> getEventsUser(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info("Received a request to return a collection of events user id={}", userId);
        return userService.getEventsUser(userId, from, size);
    }

    @PostMapping("/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createEventUser(
            @PathVariable Integer userId,

            @RequestBody NewEventDto newEventDto
    ) {
        log.info("Received a request to create a event user id={}", userId);
        return userService.createEventUser(userId, newEventDto);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto getEventUser(
            @PathVariable Integer userId,
            @PathVariable Integer eventId
    ) {
        log.info("Received a request to return a eventId={} user id={}", eventId, userId);
        return userService.getEventUser(userId, eventId);
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
            @PathVariable Integer userId,
            @PathVariable Integer eventId
    ) {
        log.info("Received a request to return >>> a eventId={} user id={}", eventId, userId);
        return userService.getRequestsParticipationInEvent(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests")
    public EventRequestStatusUpdateResult changeStatusParticipationInEvent(
            @PathVariable Integer userId,
            @PathVariable Integer eventId,
            @RequestBody EventRequestStatusUpdateRequest statusEvents
    ) {
        log.info("Received a request to change a eventId={} user id={}", eventId, userId);
        return userService.changeStatusParticipationInEvent(userId, eventId, statusEvents);
    }

    @GetMapping("/{userId}/requests")
    public ParticipationRequestDto getRequestUsersById(
            @PathVariable Integer userId
    ) {
        log.info("Received a request to return applications user={}", userId);
        return userService.getParticipation(userId);
    }

    @PostMapping("{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto createRequestParticipate(
            @PathVariable Integer userId,
            @RequestParam Integer eventId
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
