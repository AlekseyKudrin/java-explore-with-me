package ru.practicum.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class General {

    public static final DateTimeFormatter SERVER_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final String MIN_TIME = "2000-01-01 00:00:00";

    public static final String MAX_TIME = "3000-01-01 00:00:00";

    public static PageRequest toPage(Integer from, Integer size) {
        return PageRequest.of(from > 0 ? from / size : 0, size);
    }

    public static PageRequest toPage(Integer from, Integer size, Sort sort) {
        return PageRequest.of(from > 0 ? from / size : 0, size, sort);
    }
}
