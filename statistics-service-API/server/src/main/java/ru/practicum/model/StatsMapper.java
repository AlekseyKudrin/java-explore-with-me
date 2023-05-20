package ru.practicum.model;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StatsMapper {

    StatsMapper STATS_MAPPER = Mappers.getMapper(StatsMapper.class);

    Stats toViewStatsDto(Stats viewStats);
}