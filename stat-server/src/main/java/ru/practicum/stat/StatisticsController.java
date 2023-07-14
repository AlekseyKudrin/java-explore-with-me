package ru.practicum.stat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stat.dto.HitDto;
import ru.practicum.stat.dto.Stats;
import ru.practicum.stat.service.StatisticsService;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping
public class StatisticsController {

    private final StatisticsService statisticsService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public String createHit(@RequestBody HitDto hitDto) {
        log.info("Received a request to create a hit");
        return statisticsService.createHit(hitDto);
    }

    @GetMapping("/stats")
    public List<Stats> getStats(
            @RequestParam(required = false) LocalDateTime start,
            @RequestParam(required = false) LocalDateTime end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(defaultValue = "false") Boolean unique
    ) {
        log.info("Received a request to create a statistics");
        return statisticsService.getStats(start, end, uris, unique);
    }
}

