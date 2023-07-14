package ru.practicum.stat.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.stat.repository.StatisticsRepository;
import ru.practicum.stat.dto.HitDto;
import ru.practicum.stat.model.HitMapper;
import ru.practicum.stat.model.Stats;
import ru.practicum.stat.service.StatisticsService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsRepository statisticsRepository;

    @Override
    public String createHit(HitDto hitDto) {
        statisticsRepository.save(HitMapper.toHit(hitDto));
        log.info("Create hit successful");
        return ("{\"description\":\"hit saved successfully\"}");
    }

    @Override
    public List<Stats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (start == null || end == null) {
            throw new IllegalArgumentException();
        } else {
            if (start.isAfter(end)) {
                throw new IllegalArgumentException();
            }
        }
        List<Stats> getStats;
        if (uris == null || uris.isEmpty()) {
            if (unique) {
                getStats = statisticsRepository.getStatsWithoutUriUnique(start, end);
            } else {
                getStats = statisticsRepository.getStatsWithoutUriNotUnique(start, end);
            }
        } else if (unique) {
            getStats = statisticsRepository.getStatsUnique(start, end, uris);
        } else {
            getStats = statisticsRepository.getStatsNotUnique(start, end, uris);
        }
        log.info("Statistics sent");
        return getStats;
    }
}
