package ru.practicum.rating.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.event.model.Event;
import ru.practicum.rating.model.Rating;
import ru.practicum.user.model.User;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    Rating searchByUserAndEvent(User user, Event event);

    void deleteByUserAndEvent(User user, Event event);

    @Query(value = "select e.id as id, case when likes is null then 0 else likes end -" +
            "case when dislikes is null then 0 else dislikes end as rating " +
            "from events as e " +
            "left join (select event_id, count(status) as likes " +
            "from event_rating " +
            "where status = true " +
            "group by event_id) as a on e.id = a.event_id " +
            "left join (select event_id, count(status) as dislikes " +
            "from event_rating " +
            "where status = false " +
            "group by event_id) as b on e.id = b.event_id"
            , nativeQuery = true)
    List<Object[]> getRatingEvents(PageRequest pageRequest);

    @Query(value = "select u.id, b.rating as rating " +
            "from users as u " +
            "left join (select " +
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
            "group by u.id, u.name, u.email) as b on u.id = b.id"
            , nativeQuery = true)
    List<Object[]> getRatingAuthors(PageRequest pageRequest);
}
