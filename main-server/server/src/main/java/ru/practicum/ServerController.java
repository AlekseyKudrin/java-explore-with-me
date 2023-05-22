package ru.practicum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.model.Stats;
import ru.practicum.service.StatisticsService;

import java.util.List;


@Validated
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping
public class ServerController {

    private final StatisticsService statisticsService;

    @PostMapping("/hit")
    public ResponseEntity<Object> createHit(
            @RequestBody HitDto hitDto) {
        log.info("Received a request to create a hit");
        return statisticsService.createHit(hitDto);
    }

    @GetMapping("/stats")
    public List<Stats> getStats(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam Boolean unique
    ) {
        log.info("Received a request to create a statistics");
        return statisticsService.getStats(start, end, uris, unique);
    }
}
