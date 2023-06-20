package ru.practicum.rating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.event.model.Event;
import ru.practicum.rating.model.Rating;
import ru.practicum.rating.model.RatingAuthors;
import ru.practicum.rating.model.RatingEvents;
import ru.practicum.user.model.User;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    Rating searchByUserAndEvent(User user, Event event);

    void deleteByUserAndEvent(User user, Event event);

    @Query("select new ru.practicum.rating.model.RatingEvents(e, count(r.status)) " +
            "from Event e " +
            "right join Rating r on r.event=e.id " +
            "where r.status=true "+
            "group by e.id "+
            "order by count(r.status) desc"
    )
    List<RatingEvents> getRatingEvents();

    @Query("select new ru.practicum.rating.model.RatingAuthors(u, ) " +
            "from User u " +
            "right join Rating r on r.user=u.id " +
            "where ")
    List<RatingAuthors> getRatingAuthors();
}
