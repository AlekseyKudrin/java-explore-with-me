package ru.practicum.rating.model;

import org.springframework.data.domain.Sort;

public enum Sorting {
    DESC(Sort.by(Sort.Direction.DESC, "rating")),
    ASC(Sort.by(Sort.Direction.ASC, "rating"));

    private final Sort sort;

    Sorting(Sort sort) {
        this.sort = sort;
    }

    public Sort getSort() {
        return sort;
    }
}
