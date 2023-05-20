package ru.practicum.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.HitDto;
import ru.practicum.util.Constant;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HitMapper {
    public static Hit toHit(HitDto hitDto) {
        return new Hit(
                0L,
                hitDto.getApp(),
                hitDto.getUri(),
                hitDto.getIp(),
                LocalDateTime.parse(hitDto.getTimestamp(), Constant.FORMATTER)
        );
    }

}
