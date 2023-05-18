package ru.practicum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Validated
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping
public class ServerController {

    @PostMapping("/hit")
    public void createHit(
            @RequestBody HitDto hitDto) {
    }

    @GetMapping("/stats")
    public Optional<HitDto> getStats(
            @RequestParam() String start,
            @RequestParam() String end,
            @RequestParam() List<String > uris,
            @RequestParam() Boolean unique
    ) {
        return Optional.empty();
    }
}
