package ru.practicum.allDto;

import ru.practicum.event.model.EventFullDto;

import java.util.List;

public class CompilationDto {
    Integer id;
    Boolean pinned;
    String title;
    List<EventFullDto> events;
}
