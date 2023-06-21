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
            "where r.status=true " +
            "group by e.id " +
            "order by count(r.status) desc"
    )
    List<RatingEvents> getRatingEvents();

    @Query(value = "select users.id as user1, sum(case when ev.rating >= 1 then 1 when ev.rating<=-1 then -1 when ev.rating = 0 then 0 end) as rating " +
            "from users " +
            "right join (select events.*, a.likes, b.dislikes, " +
            "(case when likes is null then 0 else likes end -" +
            "case when dislikes is null then 0 else dislikes end)" +
            "as rating " +
            "from events " +
            "left join (select event_id, count(ra.status) as likes " +
            "from event_rating as ra " +
            "where status = true " +
            "group by ra.event_id) as a on id = a.event_id " +
            "left join (select event_id, count(status) as dislikes " +
            "from event_rating " +
            "where status = false " +
            "group by event_id) as b on id = b.event_id) as ev on users.id=ev.initiator " +
            "group by users.id, users.name, users.email " +
            "order by rating desc" , nativeQuery = true)

            List<RatingAuthors>getRatingAuthors();
}
