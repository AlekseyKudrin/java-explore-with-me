package ru.practicum.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.model.CompilationDto;
import ru.practicum.compilation.service.CompilationService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/compilations")
public class CompilationController {

    private final CompilationService compilationService;

    @GetMapping
    public List<CompilationDto> getCompilations(
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "false") Boolean pinned
    ) {
        log.info("Received a request to return a collections compilation of events");
        List<CompilationDto> list = compilationService.getCompilations(from, size, pinned);
        log.info("Request to return a collections compilation completed");
        return list;
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilationsById(
            @PathVariable Integer compId
    ) {
        log.info("Received a request to return a collection compilation by Id={}", compId);
        CompilationDto compilation = compilationService.getCompilationsById(compId);
        log.info("Request to return a collection compilation by Id={} completed", compId);
        return compilation;
    }
}
