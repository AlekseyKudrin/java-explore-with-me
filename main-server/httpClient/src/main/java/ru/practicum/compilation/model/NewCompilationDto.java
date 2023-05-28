package ru.practicum.compilation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class NewCompilationDto {
    List<Integer> events;
    Boolean pinned;
    String title;
}
