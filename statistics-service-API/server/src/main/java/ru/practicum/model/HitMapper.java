package ru.practicum.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.HitDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HitMapper {
    public static Hit toHit(HitDto hitDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return new Hit(
                0L,
                hitDto.getApp(),
                hitDto.getUri(),
                hitDto.getIp(),
                LocalDateTime.parse(hitDto.getTimestamp(), formatter)
        );
    }

}
