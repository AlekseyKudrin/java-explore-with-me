package ru.practicum.compilation.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.event.model.EventShortDto;
import ru.practicum.compilation.dao.CompilationRepository;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.compilation.model.CompilationDto;
import ru.practicum.compilation.model.CompilationMapper;
import ru.practicum.compilation.model.NewCompilationDto;
import ru.practicum.compilation.service.CompilationService;
import ru.practicum.event.service.impl.EventServiceImpl;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;

    private final EventServiceImpl eventService;

    @Override
    public CompilationDto createCompilation(NewCompilationDto newCompilationDto) {
        Compilation compilation = compilationRepository.save(CompilationMapper.toCompilation(newCompilationDto));
        EventShortDto list = eventService.getEventShort(1);
        return null;
    }
}
