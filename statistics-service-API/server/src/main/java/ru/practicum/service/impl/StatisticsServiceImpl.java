package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.HitDto;
import ru.practicum.dao.StatisticsRepository;
import ru.practicum.model.HitMapper;
import ru.practicum.model.Stats;
import ru.practicum.service.StatisticsService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsRepository statisticsRepository;


    @Override
    public ResponseEntity<Object> createHit(HitDto hitDto) {
        statisticsRepository.save(HitMapper.toHit(hitDto));
        return ResponseEntity.status(HttpStatus.CREATED).body("{\"description\":\"hit saved successfully\"}");
    }

    @Override
    public List<Stats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        log.info("Stats sent");
        if (uris == null || uris.isEmpty()) {
            if (unique) {
                return statisticsRepository.getStatsWithoutUriUnique(start, end);
            } else {
                return statisticsRepository.getStatsWithoutUriNotUnique(start, end);
            }
        } else if (unique) {
            return statisticsRepository.getStatsUnique(start, end, uris);
        } else {
            List<Stats> s = statisticsRepository.getStatsNotUnique(start, end, uris);
            return s;
        }
    }
}
