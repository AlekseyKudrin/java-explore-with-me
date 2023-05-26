package ru.practicum.allDto;

import ru.practicum.event.model.event.Event;

import java.util.List;

public class CompilationDto {
    Integer id;
    Boolean pinned;
    String title;
    List<Event> events;
}
