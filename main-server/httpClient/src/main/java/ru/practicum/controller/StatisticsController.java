package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.Category;
import ru.practicum.client.ServerClient;

import javax.validation.Valid;

@Slf4j
@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping
public class StatisticsController {

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

    @PatchMapping("/admin/categories/{catId}")
    public ResponseEntity<Object> changeCategory(
            @PathVariable Integer catId,
            @RequestBody @Valid Category category
    ) {
        log.info("Received a request to change a category {}", catId);
        return serverClient.changeCategory(catId, category);
    }

    @GetMapping("/users/{userId}/events")
    public ResponseEntity<Object> changeCategory(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info("Received a request to return a collection of events user id={}", userId);
        return serverClient.get(userId, from, size);
    }

    @PostMapping("/users/{userId}/events")
    public ResponseEntity<Object> changeCategory(
            @PathVariable Integer userId,
            @RequestBody @Valid Event event
    ) {
        log.info("Received a request to create a event user id={}", userId);
        return serverClient.createEvent(userId, event);
    }

}
