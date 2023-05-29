package ru.practicum.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventFullDto;
import ru.practicum.event.model.EventShortDto;
import ru.practicum.event.model.NewEventDto;
import ru.practicum.reqest.model.ParticipationRequestDto;
import ru.practicum.user.service.impl.UserServiceImpl;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {

    private final UserServiceImpl userService;

        @GetMapping("/users/{userId}/events")
    public EventShortDto getEventsUser(
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
//
//    @GetMapping("/users/{userId}/events/{eventId}")
//    public ResponseEntity<Object> getEventUser(
//            @PathVariable Integer userId,
//            @PathVariable Integer eventId
//    ) {
//        log.info("Received a request to return a eventId={} user id={}", eventId, userId);
//        return serverClient.getEventUser(userId, eventId);
//    }
//
//    @PatchMapping("/users/{userId}/events/{eventId}")
//    public ResponseEntity<Object> changeEventUser(
//            @PathVariable Integer userId,
//            @PathVariable Integer eventId
//    ) {
//        log.info("Received a request to change a eventId={} user id={}", eventId, userId);
//        return serverClient.changeEventUser(userId, eventId);
//    }
//
//    @GetMapping("/users/{userId}/events/{eventId}/requests")
//    public ResponseEntity<Object> getRequestsUserInEvent(
//            @PathVariable Integer userId,
//            @PathVariable Integer eventId
//    ) {
//        log.info("Received a request to return >>> a eventId={} user id={}", eventId, userId);
//        return serverClient.getRequestsUserInEvent(userId, eventId);
//    }
//
//    @PatchMapping("/users/{userId}/events/{eventId}/requests")
//    public ResponseEntity<Object> changeStatusParticipationInEvent(
//            @PathVariable Integer userId,
//            @PathVariable Integer eventId,
//            @RequestBody StatusEvents statusEvents
//    ) {
//        log.info("Received a request to change a eventId={} user id={}", eventId, userId);
//        return serverClient.changeStatusParticipationInEvent(userId, eventId, statusEvents);
//    }
//
//    @GetMapping("/users/{userId}/requests")
//    public ResponseEntity<Object> getRequestUsersById(
//            @PathVariable Integer userId
//    ) {
//        log.info("Received a request to return applications user={}", userId);
//        return serverClient.getRequestUsersById(userId);
//    }
//
    @PostMapping("{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto createRequestParticipate(
            @PathVariable Integer userId,
            @RequestParam Integer eventId
    ) {
        log.info("Received a request to participate in event id={}", eventId);
        return userService.createRequestParticipate(userId, eventId);
    }
//
//    @PatchMapping("/users/{userId}/requests/{requestId}/cancel")
//    public ResponseEntity<Object> cancelingParticipate(
//            @PathVariable Integer userId,
//            @PathVariable Integer requestId
//    ) {
//        log.info("Received a request to canceling participate");
//        return serverClient.cancelingParticipate(userId, requestId);
//    }

}
