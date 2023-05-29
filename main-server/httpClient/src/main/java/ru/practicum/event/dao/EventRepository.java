package ru.practicum.event.dao;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventFullDto;

import java.util.List;


public interface EventRepository extends JpaRepository<Event, Integer> {

    List<Event> findAll(Integer userId, PageRequest pageRequest);
}
