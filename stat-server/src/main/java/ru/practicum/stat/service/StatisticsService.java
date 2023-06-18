package ru.practicum.stat.service;

import ru.practicum.stat.model.HitDto;
import ru.practicum.stat.model.Stats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticsService {
    String createHit(HitDto hitDto);

    List<Stats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
