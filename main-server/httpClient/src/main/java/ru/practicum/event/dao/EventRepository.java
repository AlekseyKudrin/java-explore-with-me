package ru.practicum.event.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventFullDto;


public interface EventRepository extends JpaRepository<Event, Integer> {

}
