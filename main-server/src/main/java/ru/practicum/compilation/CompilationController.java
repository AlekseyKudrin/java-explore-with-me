package ru.practicum.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.model.CompilationDto;
import ru.practicum.compilation.service.impl.CompilationServiceImpl;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/compilations")
public class CompilationController {

    private final CompilationServiceImpl compilationService;

    @GetMapping
    public List<CompilationDto> getCompilations(
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "false") Boolean pinned
    ) {
        log.info("Received a request to return a collection of events");
        return compilationService.getCompilations(from, size, pinned);
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilationsById(
            @PathVariable Integer compId
    ) {
        log.info("Received a request to return a collection of events by Id={}", compId);
        return compilationService.getCompilationsById(compId);
    }
}
