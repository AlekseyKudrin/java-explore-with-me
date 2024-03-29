package ru.practicum.rating.service;

import ru.practicum.rating.model.RatingAuthorsDto;
import ru.practicum.rating.model.RatingDto;
import ru.practicum.rating.model.RatingEventsDto;
import ru.practicum.rating.model.Sorting;

import java.util.List;

public interface RatingService {

    RatingDto create(Integer userId, Integer eventId, Boolean state);

    void delete(Integer userId, Integer eventId);

    List<RatingEventsDto> getRatingEvents(Integer userId, Sorting sort, Integer from, Integer size);

    List<RatingAuthorsDto> getRatingAuthors(Integer userId, Sorting sort, Integer from, Integer size);
}
