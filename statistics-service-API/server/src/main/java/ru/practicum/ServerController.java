package ru.practicum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.model.Stats;
import ru.practicum.service.StatisticsService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Validated
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping
public class ServerController {

    private final StatisticsService statisticsService;

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @PostMapping("/hit")
    public ResponseEntity<Object> createHit(
            @RequestBody HitDto hitDto) {
        return statisticsService.createHit(hitDto);
    }

    @GetMapping("/stats")
    public List<Stats> getStats(
            @RequestParam() String start,
            @RequestParam() String end,
            @RequestParam() List<String> uris,
            @RequestParam() Boolean unique
    ) {
        return statisticsService.getStats(
                LocalDateTime.parse(start, FORMATTER),
                LocalDateTime.parse(end, FORMATTER),
                uris,
                unique);
    }
}
