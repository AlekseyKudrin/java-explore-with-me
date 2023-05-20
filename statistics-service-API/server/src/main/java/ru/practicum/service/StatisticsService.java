package ru.practicum.service;

import org.springframework.http.ResponseEntity;
import ru.practicum.HitDto;
import ru.practicum.model.Stats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticsService {
    ResponseEntity<Object> createHit(HitDto hitDto);

    List<Stats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);

}
