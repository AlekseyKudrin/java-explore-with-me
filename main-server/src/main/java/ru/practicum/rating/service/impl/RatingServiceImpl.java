package ru.practicum.rating.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.event.model.Event;
import ru.practicum.event.service.EventService;
import ru.practicum.rating.dto.*;
import ru.practicum.rating.model.*;
import ru.practicum.rating.repository.RatingRepository;
import ru.practicum.rating.service.RatingService;
import ru.practicum.user.model.User;
import ru.practicum.user.service.UserService;
import ru.practicum.util.General;

import javax.validation.ValidationException;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final UserService userService;

    private final EventService eventService;

    private final RatingRepository ratingRepository;

    @Override
    public RatingDto create(Integer userId, Integer eventId, Boolean state) {
        User user = userService.findUserById(userId);
        Event event = eventService.findEventById(eventId);
        if (userId.equals(event.getInitiator().getId())) {
            throw new ValidationException("user can't like himself");
        }

        Rating rating = ratingRepository.searchByUserAndEvent(user, event);
        if (rating == null) {
            rating = ratingRepository.save(RatingMapper.toRating(user, event, state));
        } else {
            rating.setStatus(state);
            rating = ratingRepository.save(rating);
        }
        return RatingMapper.toRatingDto(rating);
    }

    @Transactional
    @Override
    public void delete(Integer userId, Integer eventId) {
        User user = userService.findUserById(userId);
        Event event = eventService.findEventById(eventId);

        ratingRepository.deleteByUserAndEvent(user, event);
    }

    @Override
    public List<RatingEventsDto> getRatingEvents(Integer userId, Sorting sort, Integer from, Integer size) {
        userService.findUserById(userId);
        return ratingRepository.getRatingEvents(General.toPage(from, size, sort.getSort()))
                .stream()
                .map(i -> RatingMapper.toRatingEventsDto(eventService.getEventShortDtoByEventId(i.getEventId()), i.getRating()))
                .collect(Collectors.toList());
    }

    @Override
    public List<RatingAuthorsDto> getRatingAuthors(Integer userId, Sorting sort, Integer from, Integer size) {
        userService.findUserById(userId);
        return ratingRepository.getRatingAuthors(General.toPage(from, size, sort.getSort()))
                .stream()
                .map(i -> new RatingAuthors(((BigInteger) i[0]).intValue(), ((BigInteger) i[1]).intValue()))
                .collect(Collectors.toList())
                .stream()
                .map(i -> RatingMapper.toRatingAuthorsDto(userService.getUserShortDtoByUserId(i.getUserId()), i.getRating()))
                .collect(Collectors.toList());
    }
}
