package ru.practicum.event.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.event.model.event.Event;


public interface EventRepository extends JpaRepository<Event, Integer> {
}
