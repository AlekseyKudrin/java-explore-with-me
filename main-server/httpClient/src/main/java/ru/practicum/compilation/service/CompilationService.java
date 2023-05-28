package ru.practicum.compilation.service;

import ru.practicum.compilation.model.CompilationDto;
import ru.practicum.compilation.model.NewCompilationDto;

public interface CompilationService {

    CompilationDto createCompilation(NewCompilationDto newCompilationDto);
}
