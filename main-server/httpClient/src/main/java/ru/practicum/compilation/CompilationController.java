package ru.practicum.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin.model.UpdateEventAdminRequest;
import ru.practicum.admin.service.impl.AdminServiceImpl;
import ru.practicum.category.model.CategoryDto;
import ru.practicum.compilation.model.CompilationDto;
import ru.practicum.compilation.model.NewCompilationDto;
import ru.practicum.compilation.model.UpdateCompilationRequest;
import ru.practicum.compilation.service.impl.CompilationServiceImpl;
import ru.practicum.event.model.EventFullDto;
import ru.practicum.user.model.UserDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin")
public class CompilationController {

    private final CompilationServiceImpl compilationService;

    @GetMapping("/compilations")
    public List<CompilationDto> getCompilations(
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "false") Boolean pinned
    ) {
        log.info("Received a request to return a collection of events");
        return compilationService.getCompilations(from, size, pinned);
    }

    @GetMapping("/compilations/{compId}")
    public CompilationDto getCompilationsById(
            @PathVariable Integer compId
    ) {
        log.info("Received a request to return a collection of events by Id={}", compId);
        return compilationService.getCompilationsById(compId);
    }
}
