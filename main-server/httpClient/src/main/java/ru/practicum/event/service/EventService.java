package ru.practicum.event.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.event.model.Event;

import java.util.List;

public interface EventService {

    List<Event> getEventsUser(Integer userId, PageRequest pageRequest);
}
