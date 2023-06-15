package ru.practicum.compilation.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.event.model.EventShortDto;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompilationMapper {

    public static Compilation toCompilation(NewCompilationDto newCompilationDto) {
        Compilation compilation = new Compilation();
        compilation.setEvents(newCompilationDto.getEvents());
        compilation.setPinned(newCompilationDto.getPinned());
        compilation.setEvents(newCompilationDto.getEvents());
        compilation.setTitle(newCompilationDto.getTitle());
        return compilation;
    }

    public static CompilationDto toCompilationDto(List<EventShortDto> events, Compilation save) {
        CompilationDto compilationDto = new CompilationDto();
        compilationDto.setId(save.getId());
        compilationDto.setEvents(events);
        compilationDto.setPinned(save.getPinned());
        compilationDto.setTitle(save.getTitle());
        return compilationDto;
    }
}
