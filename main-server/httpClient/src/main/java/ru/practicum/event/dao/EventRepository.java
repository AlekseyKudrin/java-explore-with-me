package ru.practicum.event.dao;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.event.model.UpdateEventUserRequest;
import ru.practicum.event.model.Event;

import java.util.List;


public interface EventRepository extends JpaRepository<Event, Integer> {

    List<Event> findAllByInitiatorId(Integer userId, PageRequest pageRequest);

    Event findByIdAndInitiatorId(Integer userId, Integer eventId);

//    Event updateById(Integer eventId, UpdateEventUserRequest updateEventUserRequest);

    Event updateAllByAnnotation (String annotation,Event event);
}
