package ru.practicum.compilation.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.compilation.dao.CompilationRepository;
import ru.practicum.compilation.model.*;
import ru.practicum.compilation.service.CompilationService;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventShortDto;
import ru.practicum.event.service.impl.EventServiceImpl;

import java.util.ArrayList;
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
        List<EventShortDto> events = compilation.getEvents().stream().map(eventService::findEventById).map(eventService::getEventShortDto).collect(Collectors.toList());
        return CompilationMapper.toCompilationDto(events, compilation);
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
        List<EventShortDto> events = compilation.getEvents().stream().map(eventService::findEventById).map(eventService::getEventShortDto).collect(Collectors.toList());
        return CompilationMapper.toCompilationDto(events, compilationRepository.save(compilation));
    }

    @Override
    public List<CompilationDto> getCompilations(Integer from, Integer size, Boolean pinned) {
        int page = from / size;
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Compilation> compilation = compilationRepository.findAll(pageRequest).toList();
        List<CompilationDto> compilationDto = new ArrayList<>();
        for (Compilation a : compilation) {
            List<Event> eventList = a.getEvents().stream().map(eventService::findEventById).collect(Collectors.toList());
            List<EventShortDto> eventShortDtoList = eventList.stream().map(eventService::getEventShortDto).collect(Collectors.toList());
            compilationDto.add(CompilationMapper.toCompilationDto(eventShortDtoList, a));
        }
        return compilationDto;
    }

    @Override
    public CompilationDto getCompilationsById(Integer compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow();
        List<Event> eventList = compilation.getEvents().stream().map(eventService::findEventById).collect(Collectors.toList());
        List<EventShortDto> eventShortDtoList = eventList.stream().map(eventService::getEventShortDto).collect(Collectors.toList());
        return CompilationMapper.toCompilationDto(eventShortDtoList, compilation);
    }
}
