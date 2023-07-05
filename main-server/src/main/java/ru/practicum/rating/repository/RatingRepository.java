package ru.practicum.rating.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.event.model.Event;
import ru.practicum.rating.model.Rating;
import ru.practicum.rating.model.RatingEvents;
import ru.practicum.user.model.User;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    Rating searchByUserAndEvent(User user, Event event);

    @Modifying
    void deleteByUserAndEvent(User user, Event event);

    @Query(value = "select new ru.practicum.rating.model.RatingEvents(e.id as eventId, count (r1.status) - count (r2.status) as rating) " +
            "from Event as e " +
            "left join Rating as r1 on e.id = r1.event.id and r1.status = true " +
            "left join Rating as r2 on e.id = r2.event.id and r2.status = false " +
            "group by e.id")
    List<RatingEvents> getRatingEvents(PageRequest sort);

    @Query(value = "select u.id, b.rating as rating " +
            "from users as u " +
            "right join (select " +
            "u.id, sum(case when ev.rating >= 1 then 1 when ev.rating<=-1 then -1 when ev.rating = 0 then 0 end) as rating " +
            "from users as u " +
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
            "group by event_id) as b on id = b.event_id) as ev on u.id = ev.initiator " +
            "group by u.id, u.name, u.email) as b on u.id = b.id", nativeQuery = true)
    List<Object[]> getRatingAuthors(PageRequest pageRequest);
}
