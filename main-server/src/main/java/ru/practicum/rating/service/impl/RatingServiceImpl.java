package ru.practicum.rating.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventMapper;
import ru.practicum.event.service.EventService;
import ru.practicum.rating.model.*;
import ru.practicum.rating.repository.RatingRepository;
import ru.practicum.rating.service.RatingService;
import ru.practicum.user.model.User;
import ru.practicum.user.service.UserService;

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

        Rating rating = ratingRepository.searchByUserAndEvent(user, event);

        if (rating == null) {
            rating = ratingRepository.save(RatingMapper.toRating(user, event, state));
        } else {
            rating.setStatus(state);
            rating = ratingRepository.save(rating);
        }
        return RatingMapper.toRatingDto(rating);
    }

    @Override
    public void delete(Integer userId, Integer eventId) {
        User user = userService.findUserById(userId);
        Event event = eventService.findEventById(eventId);

        ratingRepository.deleteByUserAndEvent(user, event);
    }

    @Override
    public List<RatingEventsDto> getRatingEvents(Integer userId) {
        userService.findUserById(userId);

        return ratingRepository.getRatingEvents()
                .stream()
                .map(i -> RatingMapper.toRatingEventsDto(EventMapper.toEventShortDto(
                        eventService.getEventFullDto(List.of(i.getEvent())).get(0)), i.getLike()))
                .collect(Collectors.toList());
    }

    @Override
    public List<RatingAuthorsDto> getRatingAuthors(Integer userId) {
        userService.findUserById(userId);
        return ratingRepository.getRatingAuthors()
                .stream()
                .map(i -> new RatingAuthors(((BigInteger) i[0]).intValue(), ((BigInteger) i[1]).intValue()))
                .collect(Collectors.toList())
                .stream()
                .map(i -> RatingMapper.toRatingAuthorsDto(userService.getUserShortDtoByUserId(i.getId()), i.getRating()))
                .collect(Collectors.toList());
    }
}
