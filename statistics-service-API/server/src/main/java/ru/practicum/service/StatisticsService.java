package ru.practicum.service;

import org.springframework.http.ResponseEntity;
import ru.practicum.HitDto;
import ru.practicum.model.Stats;

import java.util.List;

public interface StatisticsService {
    ResponseEntity<Object> createHit(HitDto hitDto);

    List<Stats> getStats(String start, String end, List<String> uris, Boolean unique);
}
