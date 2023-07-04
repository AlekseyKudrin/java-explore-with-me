package ru.practicum.rating.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventShortDto;
import ru.practicum.user.model.User;
import ru.practicum.user.model.UserShortDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class RatingMapper {

    public static Rating toRating(User user, Event event, Boolean status) {
        Rating rating = new Rating();
        rating.setUser(user);
        rating.setEvent(event);
        rating.setStatus(status);
        return rating;
    }

    public static RatingDto toRatingDto(Rating rating) {
        return new RatingDto(
                rating.getId(),
                rating.getUser().getId(),
                rating.getEvent().getId(),
                rating.getStatus()
        );
    }

    public static RatingEventsDto toRatingEventsDto(EventShortDto eventShortDto, Long likes) {
        return new RatingEventsDto(
                eventShortDto,
                likes
        );
    }

    public static RatingAuthorsDto toRatingAuthorsDto(UserShortDto userShortDto, Integer likes) {
        return new RatingAuthorsDto(
                userShortDto,
                likes);
    }
}
