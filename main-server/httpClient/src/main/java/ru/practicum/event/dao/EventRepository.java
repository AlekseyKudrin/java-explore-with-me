package ru.practicum.event.dao;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventShort;
import ru.practicum.event.model.enums.State;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface EventRepository extends JpaRepository<Event, Integer> {

    List<Event> findAllByInitiatorId(Integer userId, PageRequest pageRequest);



    Event findByIdAndInitiatorId(Integer userId, Integer eventId);

    @Query(value = "select * from Events e " +
            "where (e.initiator in (case when ?1 is null then 0 else ?1 end) " +
            "and e.state in (case when ?2 is null then '' else ?2 end) " +
            "and e.category in (case when ?3 is null then 0 else ?3 end)) " +
            "and e.event_date between (case when ?4 is null then '1990-01-01 01:00' else ?4 end) and (case when ?5 is null then '2300-01-01 03:00' else ?5 end)"
            , nativeQuery = true
    )
    List<Event> findEventsByParameters(List<Integer> users,
                                       List<String> states,
                                       List<Integer> categories,
                                       LocalDateTime rangeStart,
                                       LocalDateTime rangeEnd,
                                       PageRequest pageRequest);

    @Query("select new ru.practicum.event.model.EventShort(e.id, e.annotation, e.category, e.participantLimit, e.eventDate, e.initiator, e.paid, e.title) " +
            "from Event e " +
            "where (lower(e.annotation) like CONCAT('%',(case when ?1 is null then '' else lower(?1) end),'%') " +
            "or lower(e.description) like CONCAT('%',(case when ?1 is null then '' else lower(?1) end),'%'))" +
            "and (?2 is null or e.category in ?2)" +
            "and (?3 is null or e.paid = ?3)" +
            "and (cast(?4 AS date) IS NULL OR e.eventDate >= ?4)" +
            "and (cast(?5 AS date) IS NULL OR e.eventDate <= ?5)")
    List<EventShort> findEventsByParametersOfUser(String text,
                                                  List<Integer> categories,
                                                  Boolean paid,
                                                  LocalDateTime rangeStart,
                                                  LocalDateTime rangeEnd,
                                                  PageRequest pageRequest);

    Optional<Event> findByIdAndState(Integer eventId, State state);
}
