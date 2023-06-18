package ru.practicum.stat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.stat.model.Hit;
import ru.practicum.stat.model.Stats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticsRepository extends JpaRepository<Hit, Long> {
    @Query("select new ru.practicum.stat.model.Stats( h.app, h.uri, count(distinct h.ip))" +
            "from  Hit h " +
            "where ( h.timestamp between :start and :end) " +
            "and h.uri IN (:uris) " +
            "group by h.app, h.uri " +
            "order by count(distinct h.ip) desc")
    List<Stats> getStatsUnique(@Param("start") LocalDateTime start,
                               @Param("end") LocalDateTime end,
                               @Param("uris") List<String> uris);

    @Query("select new ru.practicum.stat.model.Stats(h.app, h.uri, count(h.ip)) " +
            "from Hit h " +
            "where (h.timestamp between :start and :end) " +
            "and h.uri IN (:uris) " +
            "group by h.app, h.uri " +
            "order by count(h.ip) desc")
    List<Stats> getStatsNotUnique(@Param("start") LocalDateTime start,
                                  @Param("end") LocalDateTime end,
                                  @Param("uris") List<String> uris);

    @Query("select new ru.practicum.stat.model.Stats(h.app, h.uri, count(distinct h.ip)) " +
            "from Hit h " +
            "where (h.timestamp between :start and :end) " +
            "group by h.app, h.uri " +
            "order by count(distinct h.ip) desc")
    List<Stats> getStatsWithoutUriUnique(@Param("start") LocalDateTime start,
                                         @Param("end") LocalDateTime end);

    @Query("select new ru.practicum.stat.model.Stats(h.app, h.uri, count(h.ip)) " +
            "from Hit h " +
            "where (h.timestamp between :start and :end) " +
            "group by h.app, h.uri " +
            "order by count(h.ip) desc")
    List<Stats> getStatsWithoutUriNotUnique(@Param("start") LocalDateTime start,
                                            @Param("end") LocalDateTime end);
}
