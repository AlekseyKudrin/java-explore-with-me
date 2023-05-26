package ru.practicum.category.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.category.dao.CategoriesRepository;
import ru.practicum.category.service.CategoryService;


@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoriesRepository categoriesRepository;

}
