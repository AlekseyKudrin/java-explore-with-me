package ru.practicum.compilation.model;

import ru.practicum.event.model.EventShortDto;

import java.util.List;

public class CompilationDto {
    Integer id;
    Boolean pinned;
    String title;
    List<EventShortDto> events;
}
