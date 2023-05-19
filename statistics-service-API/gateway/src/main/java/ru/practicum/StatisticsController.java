package ru.practicum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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
        return statisticsClient.create(hitDto);
    }

    @GetMapping("/stats")
    public ResponseEntity<Object> getStats(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam List<String> uris,
            @RequestParam(defaultValue = "false") Boolean unique
    ) {
        return statisticsClient.getStats(start, end, uris, unique);
    }
}
