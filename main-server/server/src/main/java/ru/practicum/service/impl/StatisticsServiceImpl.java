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
import ru.practicum.util.Constant;

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
        log.info("Create hit successful");
        return ResponseEntity.status(HttpStatus.CREATED).body("{\"description\":\"hit saved successfully\"}");
    }

    @Override
    public List<Stats> getStats(String start, String end, List<String> uris, Boolean unique) {
        LocalDateTime startConvert = LocalDateTime.parse(start, Constant.FORMATTER);
        LocalDateTime endConvert = LocalDateTime.parse(end, Constant.FORMATTER);
        List<Stats> getStats;
        if (uris == null || uris.isEmpty()) {
            if (unique) {
                getStats = statisticsRepository.getStatsWithoutUriUnique(startConvert, endConvert);
            } else {
                getStats = statisticsRepository.getStatsWithoutUriNotUnique(startConvert, endConvert);
            }
        } else if (unique) {
            getStats = statisticsRepository.getStatsUnique(startConvert, endConvert, uris);
        } else {
            getStats = statisticsRepository.getStatsNotUnique(startConvert, endConvert, uris);
        }
        log.info("Statistics sent");
        return getStats;
    }
}
