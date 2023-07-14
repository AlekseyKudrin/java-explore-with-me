package ru.practicum.rating.service;

import ru.practicum.rating.dto.RatingAuthorsDto;
import ru.practicum.rating.dto.RatingDto;
import ru.practicum.rating.dto.RatingEventsDto;
import ru.practicum.rating.service.impl.Sorting;

import java.util.List;

public interface RatingService {

    RatingDto create(Integer userId, Integer eventId, Boolean state);

    void delete(Integer userId, Integer eventId);

    List<RatingEventsDto> getRatingEvents(Integer userId, Sorting sort, Integer from, Integer size);

    List<RatingAuthorsDto> getRatingAuthors(Integer userId, Sorting sort, Integer from, Integer size);
}
