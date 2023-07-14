package ru.practicum.compilation.service;

import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;

import java.util.List;

public interface CompilationService {

    CompilationDto createCompilation(NewCompilationDto newCompilationDto);

    void deleteCompilation(Integer comId);

    CompilationDto changeCompilation(Integer comId, UpdateCompilationRequest compilation);

    List<CompilationDto> getCompilations(Integer from, Integer size, Boolean pinned);

    CompilationDto getCompilationsById(Integer compId);
}
