package ru.practicum.compilation.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.reqest.model.ParticipationRequestDto;
import ru.practicum.reqest.model.Request;

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
}
