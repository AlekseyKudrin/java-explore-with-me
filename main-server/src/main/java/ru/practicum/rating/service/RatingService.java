package ru.practicum.rating.service;

import ru.practicum.rating.model.RatingAuthorDto;
import ru.practicum.rating.model.RatingDto;
import ru.practicum.rating.model.RatingEventsDto;

import java.util.List;

public interface RatingService {

    RatingDto create(Integer userId, Integer eventId, Boolean state);

    void delete(Integer userId, Integer eventId);

    List<RatingEventsDto> getRatingEvents(Integer userId);

    List<RatingAuthorDto> getRatingAuthors(Integer userId);
}
