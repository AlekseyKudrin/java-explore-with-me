package ru.practicum.rating;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.rating.model.RatingAuthorsDto;
import ru.practicum.rating.model.RatingDto;
import ru.practicum.rating.model.RatingEventsDto;
import ru.practicum.rating.model.Sorting;
import ru.practicum.rating.service.RatingService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/rating")

public class RatingController {

    private final RatingService ratingService;

    @PostMapping("{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public RatingDto create(
            @PathVariable Integer userId,
            @RequestParam Integer eventId,
            @RequestParam Boolean status
    ) {
        log.info("Received a request to add a like by a user id={}", userId);
        RatingDto ratingDto = ratingService.create(userId, eventId, status);
        log.info("Request to add a like by a user id={} completed", userId);
        return ratingDto;
    }

    @DeleteMapping("{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Integer userId,
            @RequestParam Integer eventId
    ) {
        log.info("Received a request to delete a like by a user id={}", userId);
        ratingService.delete(userId, eventId);
        log.info("Request to delete a like by a user id={} completed", userId);
    }

    @GetMapping("/events/{userId}")
    public List<RatingEventsDto> getRatingEvents(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "DESC") Sorting sort,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info("Received a request to return rating events from user id={}", userId);
        List<RatingEventsDto> ratingEventDto = ratingService.getRatingEvents(userId, sort, from, size);
        log.info("Request to return rating events from user id={} completed", userId);
        return ratingEventDto;
    }

    @GetMapping("/authors/{userId}")
    public List<RatingAuthorsDto> getRatingAuthors(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "DESC") Sorting sort,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info("Received a request to return rating authors from user id={}", userId);
        List<RatingAuthorsDto> ratingAuthorsDto = ratingService.getRatingAuthors(userId, sort, from, size);
        log.info("Request to return rating authors from user id={} completed", userId);
        return ratingAuthorsDto;
    }

}
