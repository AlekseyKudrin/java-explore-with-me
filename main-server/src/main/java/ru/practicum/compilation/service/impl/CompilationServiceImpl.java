package ru.practicum.compilation.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.practicum.compilation.model.*;
import ru.practicum.compilation.repository.CompilationRepository;
import ru.practicum.compilation.service.CompilationService;
import ru.practicum.event.model.EventMapper;
import ru.practicum.event.model.EventShortDto;
import ru.practicum.event.service.EventService;
import ru.practicum.exceptionHandler.exception.ValueNotFoundDbException;
import ru.practicum.util.General;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.compilation.model.CompilationMapper.toCompilationDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;

    private final EventService eventService;

    @Override
    public CompilationDto createCompilation(NewCompilationDto newCompilationDto) {
        Compilation compilation = compilationRepository.save(CompilationMapper.toCompilation(newCompilationDto));

        return toCompilationDto(receivingEvents(compilation), compilation);
    }

    @Override
    public void deleteCompilation(Integer comId) {
        try {
            compilationRepository.deleteById(comId);
        } catch (EmptyResultDataAccessException e) {
            throw new ValueNotFoundDbException("User with id=" + comId + " was not found");
        }
    }

    @Override
    public CompilationDto changeCompilation(Integer comId, UpdateCompilationRequest updateCompilation) {
        Compilation compilation = compilationRepository.findById(comId).orElseThrow(
                () -> new ValueNotFoundDbException("Compilation with id=" + comId + " was not found"));

        if (updateCompilation.getEvents() != null) compilation.setEvents(updateCompilation.getEvents());
        if (updateCompilation.getPinned() != null) compilation.setPinned(updateCompilation.getPinned());
        if (updateCompilation.getTitle() != null) compilation.setTitle(updateCompilation.getTitle());

        return toCompilationDto(receivingEvents(compilation), compilationRepository.save(compilation));
    }

    @Override
    public List<CompilationDto> getCompilations(Integer from, Integer size, Boolean pinned) {
        List<Compilation> compilation = compilationRepository.findAll(General.toPage(from, size)).toList();

        return compilation.stream()
                .map(i -> toCompilationDto(receivingEvents(i), i))
                .collect(Collectors.toList());
    }

    @Override
    public CompilationDto getCompilationsById(Integer comId) {
        Compilation compilation = compilationRepository.findById(comId).orElseThrow(
                () -> new ValueNotFoundDbException("Compilation with id=" + comId + " was not found"));

        return toCompilationDto(receivingEvents(compilation), compilation);
    }

    private List<EventShortDto> receivingEvents(Compilation comp) {
        return eventService.getEventFullDto(comp.getEvents()
                        .stream()
                        .map(eventService::findEventById)
                        .collect(Collectors.toList()))
                .stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }
}
