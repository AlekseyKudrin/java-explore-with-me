package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.HitDto;
import ru.practicum.client.StatisticsClient;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping
public class StatisticsController {

    private final StatisticsClient statisticsClient;

    @PostMapping("/hit")
    public ResponseEntity<Object> createHit(
            @RequestBody @Valid HitDto hitDto
    ) {
        log.info("Received a request to create a hit");
        return statisticsClient.create(hitDto);
    }

    @GetMapping("/stats")
    public ResponseEntity<Object> getStats(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(defaultValue = "false") Boolean unique
    ) {
        log.info("Received a request to create a statistics");
        return statisticsClient.getStats(start, end, uris, unique);
    }
}
