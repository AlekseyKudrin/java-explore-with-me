package ru.practicum.event.dao;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventShort;
import ru.practicum.event.model.enums.State;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface EventRepository extends JpaRepository<Event, Integer>, QuerydslPredicateExecutor<Event> {

    List<Event> findAllByInitiatorId(Integer userId, PageRequest pageRequest);


    Event findByIdAndInitiatorId(Integer userId, Integer eventId);

    @Query(value = "select * from EVENTS as e " +
            "where (case when ?1 then (e.initiator is not null) else e.INITIATOR in ?1 end) "
//            "and (e.state in ?2)" +1
//            "and (e.category in ?3)" +
//            "and ((cast(?4 AS date) IS NULL OR e.eventDate >= (?4)))" +
//            "and ((cast(?5 AS date) IS NULL OR e.eventDate <= (?5)))"
            , nativeQuery = true
    )
    List<Event> findEventsByParameters(List<Integer> users);

    @Query("select new ru.practicum.event.model.EventShort(e.id, e.annotation, e.category, e.participantLimit, e.eventDate, e.initiator, e.paid, e.title) " +
            "from Event e " +
            "where (?1 is null or ((lower(e.annotation) like lower(CONCAT('%', ?1,'%')) " +
            "or lower(e.description) like lower(CONCAT('%', ?1,'%')))))" +
            "and (?2 is null or e.category in ?2)" +
            "and (?3 is null or e.paid = ?3)" +
            "and (cast(?4 as date ) IS NULL OR e.eventDate >= ?4)" +
            "and (cast(?5 as date ) IS NULL OR e.eventDate <= ?5)"
    )
    List<EventShort> findEventsByParametersOfUser(String text,
                                                  List<Integer> categories,
                                                  Boolean paid,
                                                  LocalDateTime rangeStart,
                                                  LocalDateTime rangeEnd,
                                                  PageRequest pageRequest);

    Optional<Event> findByIdAndState(Integer eventId, State state);
}