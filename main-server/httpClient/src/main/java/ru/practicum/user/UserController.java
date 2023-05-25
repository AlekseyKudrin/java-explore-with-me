//package ru.practicum.user;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import ru.practicum.allDto.Event;
//import ru.practicum.allDto.StatusEvents;
//
//import javax.validation.Valid;
//
//@Slf4j
//@RestController
//@RequiredArgsConstructor
//@RequestMapping(path = "/users")
//public class UserController {
//
//    @GetMapping("/users/{userId}/events")
//    public ResponseEntity<Object> getEventsUser(
//            @PathVariable Integer userId,
//            @RequestParam(defaultValue = "0") Integer from,
//            @RequestParam(defaultValue = "10") Integer size
//    ) {
//        log.info("Received a request to return a collection of events user id={}", userId);
//        return serverClient.getEventsUser(userId, from, size);
//    }
//
//    @PostMapping("/users/{userId}/events")
//    public ResponseEntity<Object> createEventUser(
//            @PathVariable Integer userId,
//            @RequestBody @Valid Event event
//    ) {
//        log.info("Received a request to create a event user id={}", userId);
//        return serverClient.createEventUser(userId, event);
//    }
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
//    @PostMapping("/users/{userId}/requests")
//    public ResponseEntity<Object> createRequestParticipate(
//            @PathVariable Integer userId,
//            @RequestParam Integer eventId
//    ) {
//        log.info("Received a request to participate in event id={}", eventId);
//        return serverClient.createRequestParticipate(userId, eventId);
//    }
//
//    @PatchMapping("/users/{userId}/requests/{requestId}/cancel")
//    public ResponseEntity<Object> cancelingParticipate(
//            @PathVariable Integer userId,
//            @PathVariable Integer requestId
//    ) {
//        log.info("Received a request to canceling participate");
//        return serverClient.cancelingParticipate(userId, requestId);
//    }
//
//}