package ru.practicum.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class General {

    public static final String MIN_TIME = "2000-01-01 00:00:00";

    public static final String MAX_TIME = "3000-01-01 00:00:00";

    public static PageRequest toPage(Integer from, Integer size) {
        return PageRequest.of(from > 0 ? from / size : 0, size);
    }
}
