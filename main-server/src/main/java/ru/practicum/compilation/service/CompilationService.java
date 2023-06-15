package ru.practicum.compilation.service;

import ru.practicum.compilation.model.CompilationDto;
import ru.practicum.compilation.model.NewCompilationDto;
import ru.practicum.compilation.model.UpdateCompilationRequest;

import java.util.List;

public interface CompilationService {

    CompilationDto createCompilation(NewCompilationDto newCompilationDto);

    void deleteComplation(Integer comId);

    CompilationDto changeCompilation(Integer comId, UpdateCompilationRequest compilation);

    List<CompilationDto> getCompilations(Integer from, Integer size, Boolean pinned);

    CompilationDto getCompilationsById(Integer compId);
}
