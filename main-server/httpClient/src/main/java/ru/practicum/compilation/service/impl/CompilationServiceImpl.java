package ru.practicum.compilation.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.compilation.model.*;
import ru.practicum.event.model.EventShortDto;
import ru.practicum.compilation.dao.CompilationRepository;
import ru.practicum.compilation.service.CompilationService;
import ru.practicum.event.service.impl.EventServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;

    private final EventServiceImpl eventService;

    @Override
    public CompilationDto createCompilation(NewCompilationDto newCompilationDto) {
        Compilation compilation = compilationRepository.save(CompilationMapper.toCompilation(newCompilationDto));
        List<EventShortDto> events = compilation.getEvents().stream().map(eventService::getEvent).map(eventService::getEventShortDto).collect(Collectors.toList());
        return CompilationMapper.toCompilationDto(events,compilation);
    }

    @Override
    public void deleteComplation(Integer comId) {
        compilationRepository.deleteById(comId);
    }

    @Override
    public CompilationDto changeCompilation(Integer comId, UpdateCompilationRequest updateCompilation) {
        Compilation compilation = compilationRepository.findById(comId).orElseThrow();
        if (updateCompilation.getEvents() != null) {
            compilation.setEvents(updateCompilation.getEvents());
        }
        if (updateCompilation.getPinned() != null) {
            compilation.setPinned(updateCompilation.getPinned());
        }
        if (updateCompilation.getTitle() != null) {
            compilation.setTitle(updateCompilation.getTitle());
        }
        List<EventShortDto> events = compilation.getEvents().stream().map(eventService::getEvent).map(eventService::getEventShortDto).collect(Collectors.toList());
        return CompilationMapper.toCompilationDto(events, compilationRepository.save(compilation));
    }
}
