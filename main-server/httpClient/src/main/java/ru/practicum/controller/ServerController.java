package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.*;
import ru.practicum.client.ServerClient;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping
public class ServerController {

    private final ServerClient serverClient;

    @GetMapping("/compilations")
    public ResponseEntity<Object> getCompilations(
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "false") Boolean pinned
    ) {
        log.info("Received a request to return a collection of events");
        return serverClient.getCompilations(from, size, pinned);
    }

    @GetMapping("/compilations/{compId}")
    public ResponseEntity<Object> getCompilationsById(
            @PathVariable Integer compId
    ) {
        log.info("Received a request to return a collection of events by Id={}", compId);
        return serverClient.getCompilationsById(compId);
    }

    @PostMapping("/admin/categories")
    public ResponseEntity<Object> createCategory(
            @RequestBody @Valid Category category
    ) {
        log.info("Received a request to create a category");
        return serverClient.createCategory(category);
    }

    @DeleteMapping("/admin/categories/{catId}")
    public ResponseEntity<Object> deleteCategory(
            @PathVariable Integer catId
    ) {
        log.info("Received a request to delete a category {}", catId);
        return serverClient.deleteCategory(catId);
    }

    @PatchMapping("/admin/categories/{catId}")
    public ResponseEntity<Object> changeCategory(
            @PathVariable Integer catId,
            @RequestBody @Valid Category category
    ) {
        log.info("Received a request to change a category {}", catId);
        return serverClient.patchCategory(catId, category);
    }

    @GetMapping("/users/{userId}/events")
    public ResponseEntity<Object> getEventsUser(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info("Received a request to return a collection of events user id={}", userId);
        return serverClient.getEventsUser(userId, from, size);
    }

    @PostMapping("/users/{userId}/events")
    public ResponseEntity<Object> createEventUser(
            @PathVariable Integer userId,
            @RequestBody @Valid Event event
    ) {
        log.info("Received a request to create a event user id={}", userId);
        return serverClient.createEventUser(userId, event);
    }

    @GetMapping("/users/{userId}/events/{eventId}")
    public ResponseEntity<Object> getEventUser(
            @PathVariable Integer userId,
            @PathVariable Integer eventId
    ) {
        log.info("Received a request to return a eventId={} user id={}", eventId, userId);
        return serverClient.getEventUser(userId, eventId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}")
    public ResponseEntity<Object> changeEventUser(
            @PathVariable Integer userId,
            @PathVariable Integer eventId
    ) {
        log.info("Received a request to change a eventId={} user id={}", eventId, userId);
        return serverClient.changeEventUser(userId, eventId);
    }

    @GetMapping("/users/{userId}/events/{eventId}/requests")
    public ResponseEntity<Object> getRequestsUserInEvent(
            @PathVariable Integer userId,
            @PathVariable Integer eventId
    ) {
        log.info("Received a request to return >>> a eventId={} user id={}", eventId, userId);
        return serverClient.getRequestsUserInEvent(userId, eventId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests")
    public ResponseEntity<Object> changeStatusParticipationInEvent(
            @PathVariable Integer userId,
            @PathVariable Integer eventId,
            @RequestBody StatusEvents statusEvents
    ) {
        log.info("Received a request to change a eventId={} user id={}", eventId, userId);
        return serverClient.changeStatusParticipationInEvent(userId, eventId, statusEvents);
    }

    @GetMapping("/categories")
    public ResponseEntity<Object> getCategories(
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info("Received a request to return category list");
        return serverClient.getCategories(from, size);
    }

    @GetMapping("/categories/{catId}")
    public ResponseEntity<Object> getCategoryById(
            @PathVariable Integer catId
    ) {
        log.info("Received a request to return category by Id={}", catId);
        return serverClient.getCategoryById(catId);
    }

    @GetMapping("/admin/events")
    public ResponseEntity<Object> searchEvents(
            @RequestParam List<Integer> users,
            @RequestParam List<Integer> states,
            @RequestParam List<Integer> categories,
            @RequestParam String rangeStart,
            @RequestParam String rangeEnd,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info("Received a request to event search");
        return serverClient.searchEvents(
                users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/admin/events/{eventId}")
    public ResponseEntity<Object> changeEventAndStatus(
            @PathVariable Integer eventId,
            @RequestBody Event event
    ) {
        log.info("Received a request to change event id={}", eventId);
        return serverClient.changeEventAndStatus(eventId, event);
    }

    @GetMapping("/events")
    public ResponseEntity<Object> getEvents(
            @RequestParam String text,
            @RequestParam List<Integer> categories,
            @RequestParam Boolean paid,
            @RequestParam String rangeStart,
            @RequestParam String rangeEnd,
            @RequestParam Boolean onlyAvailable,
            @RequestParam String sort,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info("Received a request to return events list");
        return serverClient.getEvents(
                text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, from, size);
    }

    @GetMapping("/users/{userId}/requests")
    public ResponseEntity<Object> getRequestUsersById(
            @PathVariable Integer userId
    ) {
        log.info("Received a request to return applications user={}", userId);
        return serverClient.getRequestUsersById(userId);
    }

    @PostMapping("/users/{userId}/requests")
    public ResponseEntity<Object> createRequestParticipate(
            @PathVariable Integer userId,
            @RequestParam Integer eventId
    ) {
        log.info("Received a request to participate in event id={}", eventId);
        return serverClient.createRequestParticipate(userId, eventId);
    }

    @PatchMapping("/users/{userId}/requests/{requestId}/cancel")
    public ResponseEntity<Object> cancelingParticipate(
            @PathVariable Integer userId,
            @PathVariable Integer requestId
    ) {
        log.info("Received a request to canceling participate");
        return serverClient.cancelingParticipate(userId, requestId);
    }


    @GetMapping("/admin/users")
    public ResponseEntity<Object> getUsers(
            @RequestParam List<Integer> ids,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info("Received a request to return user list");
        return serverClient.getUsers(ids, from, size);
    }

    @PostMapping("/admin/users")
    public ResponseEntity<Object> createUser(
            @RequestBody UserDto userDto
    ) {
        log.info("Received a request to create user");
        return serverClient.createUser(userDto);
    }

    @DeleteMapping("/admin/users/{userId}")
    public ResponseEntity<Object> createUser(
            @PathVariable Integer userId
    ) {
        log.info("Received a request to delete user id={}", userId);
        return serverClient.deleteUser(userId);
    }

    @PostMapping("/admin/compilations")
    public ResponseEntity<Object> createCompilation(
            @RequestBody Compilation compilation
    ) {
        log.info("Received a request to create compilation");
        return serverClient.createCompilation(compilation);
    }

    @DeleteMapping("/admin/compilations/{comId}")
    public ResponseEntity<Object> deleteCompilation(
            @PathVariable Integer comId
    ) {
        log.info("Received a request to delete compilation id={}", comId);
        return serverClient.deleteCompilation(comId);
    }

    @PatchMapping("/admin/compilations/{comId}")
    public ResponseEntity<Object> changeCompilation(
            @PathVariable Integer comId,
            @RequestBody Compilation compilation
    ) {
        log.info("Received a request to change compilation id={}", comId);
        return serverClient.cangeCompilation(comId, compilation);
    }
}
